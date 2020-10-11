(ns doorpe.backend.my-profile
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request doc-object-id->str]]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.query :as query]))

(defn customer-my-profile
  [customer-id]
  (let [coll "customers"
        key :_id
        value (object-id customer-id)
        res (-> (query/retreive-one-by-custom-key-value coll key value)
                doc-object-id->str)]
    (response/response res)))

  (defn service-provider-my-profile
    [service-provider-id]
    (let [coll "serviceProviders"
          key :_id
          value (object-id service-provider-id)
          res (-> (query/retreive-one-by-custom-key-value coll key value)
                  doc-object-id->str)]
      (response/response res)))

(defn my-profile
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          coll "authTokens"
          res (-> (query/retreive-one-by-custom-key-value coll :token token))
          user-id (:user-id res)
          user-type (:user-type res)]
      (cond
        (= "customer" user-type) (customer-my-profile user-id)
        (= "service-provider" user-type) (service-provider-my-profile user-id)))))
