(ns doorpe.backend.my-bookings
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request docs-object-id->str docs-custom-object-id->str]]
            [monger.util :refer [object-id]]
            [doorpe.backend.util :refer [img->base64]]
            [monger.operators :refer [$or]]
            [tick.core :as time]
            [doorpe.backend.db.query :as query]))

(defn transform-booking-data-for-customer
  [{:keys [_id service-provider-id service-id booking-on service-on service-time latitude longitude status]}]
  (let [service-provider-res (query/retreive-one-by-custom-key-value "serviceProviders" :_id (object-id service-provider-id))

        service-provider-name (:name service-provider-res)
        service-provider-address (:address service-provider-res)
        service-provider-img (:img service-provider-res)


        services-res (query/retreive-one-by-custom-key-value "services" :_id (object-id service-id))
        service-name (:name services-res)
        doc {:booking-id _id
             :service-provider-name service-provider-name
             :service-provider-address service-provider-address
             :service-name service-name
             :booking-on (str booking-on)
             :service-on (str service-on)
             :service-time (str service-time)
             :latitude latitude
             :longitude longitude
             :status status
             :img service-provider-img}
        res (img->base64 doc)]
    res))

(defn show-customer-my-bookings
  [customer-id]
  (let [coll "bookings"
        pending {:status "pending"}
        accepted {:status "accepted"}
        ref {:customer-id (object-id customer-id)
             $or  [pending
                   accepted]}
        booking-res (-> (query/retreive-all-by-custom-ref coll ref)
                        docs-object-id->str
                        (docs-custom-object-id->str :customer-id)
                        (docs-custom-object-id->str :service-provider-id)
                        (docs-custom-object-id->str :service-id)
                        (docs-custom-object-id->str :review-id))]
    (if (> (count booking-res) 0)
      (response/response (pmap transform-booking-data-for-customer
                               booking-res))
      (response/response nil))))


(defn transform-booking-data-for-service-provider
  [{:keys [_id  service-id booking-on customer-id service-on service-time latitude longitude status]}]
  (let [customers-res (query/retreive-one-by-custom-key-value "customers" :_id (object-id customer-id))

        customer-name (:name customers-res)
        customer-address (:address customers-res)
        customer-img (:img customers-res)

        services-res (query/retreive-one-by-custom-key-value "services" :_id (object-id service-id))
        service-name (:name services-res)
        doc {:booking-id _id
             :customer-name customer-name
             :customer-address customer-address
             :service-name service-name
             :booking-on (str booking-on)
             :service-on (str service-on)
             :service-time (str service-time)
             :latitude latitude
             :longitude longitude
             :status status
             :img customer-img}
        res (img->base64 doc)]
    res))

(defn show-service-provider-my-bookings
  [service-provider-id]
  (let [coll "bookings"
        pending {:status "pending"}
        accepted {:status "accepted"}
        ref {:service-provider-id (object-id service-provider-id)
             $or  [pending
                   accepted]}
        booking-res (-> (query/retreive-all-by-custom-ref coll ref)
                        docs-object-id->str
                        (docs-custom-object-id->str :customer-id)
                        (docs-custom-object-id->str :service-provider-id)
                        (docs-custom-object-id->str :service-id)
                        (docs-custom-object-id->str :review-id))]
    (if (> (count booking-res) 0)
      (response/response (pmap transform-booking-data-for-service-provider
                               booking-res))
      (response/response nil))))

;; (show-service-provider-my-bookings "5f73532bc6da852d7cb61a3d")
(defn my-bookings
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          coll "authTokens"
          res (-> (query/retreive-one-by-custom-key-value coll :token token))
          user-id (:user-id res)
          user-type (:user-type res)]
      (cond
        (= "customer" user-type) (show-customer-my-bookings user-id)
        (= "service-provider" user-type) (show-service-provider-my-bookings user-id)))))

; curl  POST -H "authorization: Token ZVJdb1lzhAeQcmSl1D1fPeWKAeoq+NVLqENkoWmyTnc=" -i  "http://localhost:7000/my-bookings"
