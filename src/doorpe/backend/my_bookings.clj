(ns doorpe.backend.my-bookings
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request docs-object-id->str docs-custom-object-id->str]]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.query :as query]
            [monger.operators :refer [$or]]))

(defn show-customer-my-bookings
  [customer-id]
  (let [coll "bookings"
        pending {:status "pending"}
        accepted {:status "accepted"}
        ref {:customer-id (object-id customer-id)
             $or  [pending
                   accepted]}
        res (-> (query/retreive-all-by-custom-ref coll ref)
                docs-object-id->str
                (docs-custom-object-id->str :customer-id)
                (docs-custom-object-id->str :service-provider-id)
                (docs-custom-object-id->str :service-id)
                (docs-custom-object-id->str :review-id))]
    (if res
      (response/response res)
      (response/response nil))))

(defn show-service-provider-my-bookings
  [service-provider-id]
  (response/response "sp"))

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
