(ns doorpe.backend.reject-booking
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details]]
            [tick.core :as time]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$set]]
            [doorpe.backend.server.send-email :refer [send-email]]
            [doorpe.backend.db.util :refer [booking-id->customer-details]]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.command :as command]))

(defn reject-booking
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
                      :service-provider-id (object-id user-id)}
          doc {$set {:status "rejected"
                     :rejection-date (time/today)
                     :rejection-reason "rejection reason......"}}]
      (if (and (= "service-provider" user-type) (command/update-doc coll conditions doc))
        (let [customer (-> booking-id
                           booking-id->customer-details)
              name (:name customer)
              email-id (:email customer)
              subject "DoorPe - Booking Rejected"
              body (str  "<p><strong>" name "</strong> your booking has been rejected by our service provider, please login into your account for more info.</p>")
              email-success? (send-email email-id subject body)]
          (response/response {:status true}))
        (response/response {:status false})))))
