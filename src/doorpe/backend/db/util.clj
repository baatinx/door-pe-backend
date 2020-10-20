(ns doorpe.backend.db.util
  (:require [doorpe.backend.util :refer [doc-custom-object-id->str]]
            [doorpe.backend.db.query :as query]))

(defn token->token-details
  [token]
  (let [coll "authTokens"
        res (-> (query/retreive-one-by-custom-key-value coll :token token)
                (doc-custom-object-id->str :user-id))
        user-id (:user-id res)
        user-type (:user-type res)]
    {:user-id user-id
     :user-type user-type}))

(defn booking-id->customer-details
  [booking-id]
  (let [coll "bookings"
        customer-id  (-> (query/retreive-by-id coll booking-id)
                         :customer-id
                         str)]
    (query/retreive-by-id "customers" customer-id)))

(defn booking-id->service-provider-details
  [booking-id]
  (let [coll "bookings"
        service-provider-id  (-> (query/retreive-by-id coll booking-id)
                                 :service-provider-id
                                 str)]
    (query/retreive-by-id "serviceProviders" service-provider-id)))

(defn charges-per-booking
  []
  30)