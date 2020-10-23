(ns doorpe.backend.accept-booking
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details booking-id->customer-details]]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$set]]
            [doorpe.backend.server.send-email :refer [send-email]]
            [doorpe.backend.db.command :as command]))

(defn accept-booking
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
          doc {$set {:status "accepted"}}]
      (if (and (= "service-provider" user-type) (command/update-doc coll conditions doc))
        (let [customer (-> booking-id
                           booking-id->customer-details)
              name (:name customer)
              email-id (:email customer)
              subject "DoorPe - Booking Accepted"
              body (str  "<p><strong>" name "</strong> your booking has been accepted by our service provider, please login into your account for more info.</p>")
              email-success? (future (send-email email-id subject body))]
          (response/response {:status true}))
        (response/response {:status false})))))
