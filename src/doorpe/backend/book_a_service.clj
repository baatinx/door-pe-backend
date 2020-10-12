(ns doorpe.backend.book-a-service
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [str->int extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details]]
            [tick.core :as time]
            [monger.util :refer [object-id]]
            [doorpe.backend.server.send-email :refer [send-email]]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.ingestion :as insert]))

(defn book-a-service
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          {user-id :user-id user-type :user-type} (token->token-details token)
          {:keys [service-provider-id service-id service-on service-time service-charges charges latitude longitude]} (:params req)
          coll "bookings"
          id (object-id)
          status "pending"
          booking-on (time/today)
          doc {:_id id
               :customer-id (object-id user-id)
               :service-provider-id (object-id service-provider-id)
               :service-id (object-id service-id)
               :booking-on booking-on
               :service-on (time/date service-on)
               :service-time (time/time service-time)
               :service-charges (str->int service-charges)
               :charges (str->int charges)
               :status status
               :latitude latitude
               :longitude longitude}
          res (and (= "customer" user-type) (insert/doc coll doc))]
      (if res
        (let [coll "serviceProviders"
              service-provider-details (query/retreive-by-id coll service-provider-id)
              name (:name service-provider-details)
              email-id (:email service-provider-details)
              subject "DoorPe - New Booking"
              body (str  "<p><strong>" name "</strong>, someone needs your service, please login into your account for more info.</p>")
              success? (send-email email-id subject body)]
          (response/response {:status true}))
        (response/response {:insert-status false})))))