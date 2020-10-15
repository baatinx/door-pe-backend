(ns doorpe.backend.cancel-booking
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details booking-id->service-provider-details]]
            [tick.core :as time]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$set]]
            [doorpe.backend.server.send-email :refer [send-email]]
            [doorpe.backend.db.command :as command]))

(defn cancel-booking
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [booking-id (-> req
                         :params
                         :booking-id)
          token (extract-token-from-request req)

          ;; TBD
          ;; check booking status first, usertype, add a new field cancelation resaon ...

          {user-id :user-id user-type :user-type} (token->token-details token)
          coll "bookings"
          conditions {:_id (object-id booking-id)
                      :customer-id (object-id user-id)}
          doc {$set {:status "canceled"
                     :cancelation-date (time/today)
                     :cancelation-reason "cancelation reason......"}}
          res (and  (= "customer" user-type) (command/update-doc coll conditions doc))]
      (if res
        (let [service-provider (-> booking-id
                                   booking-id->service-provider-details)
              name (:name service-provider)
              email-id (:email service-provider)
              subject "DoorPe - Booking Canceled"
              body (str  "<p><strong>" name "</strong>, your client has canceled the booking, please login into your account for more info.</p>")
              email-success? (send-email email-id subject body)]
          (response/response {:status true}))
        (response/response {:status false})))))