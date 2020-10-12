(ns doorpe.backend.book-a-service
  (:require [ring.util.response :as response]
            [tick.core :as time]
            [monger.util :refer [object-id]]
            [doorpe.backend.util :refer [str->int]]
            [doorpe.backend.server.send-email :refer [send-email]]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.ingestion :as insert]))

(defn service-provider-id->service-provider-mail
  [service-provider-id]
  (let [coll "serviceProviders"]
    (->
     (query/retreive-by-id coll service-provider-id)
     :email
     str)))

(defn book-a-service
  [req]
  (let [{:keys [customer-id service-provider-id service-id service-on service-time service-charges charges latitude longitude]} (:params req)
        coll "bookings"
        id (object-id)
        status "pending"
        booking-on (time/today)
        doc {:_id id
             :customer-id (object-id customer-id)
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
        res (insert/doc coll doc)]
    (if res
      (let [email-id (-> service-provider-id
                         service-provider-id->service-provider-mail)
            success? (send-email email-id "DoorPe - New Booking" "<p>Someone needs your service, please login into your account for more info.</p>")]
        (response/response {:status true}))
      (response/response {:insert-status false}))))