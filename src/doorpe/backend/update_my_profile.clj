(ns doorpe.backend.update-my-profile
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request doc-object-id->str str->int]]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$set]]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.command :as command]))

(defn update-customer-profile
  [customer-id {:keys [name email age gender district address landmark pin-code latitude longitude]}]
  (let [id (object-id customer-id)
        coll "customers"
        doc {$set {:name name
                   :email email
                   :age (str->int age)
                   :gender gender
                   :district district
                   :address address
                   :landmark landmark
                   :pin-code (str->int pin-code)
                   :latitude latitude
                   :longitude longitude}}]
    (command/update-doc-by-id coll id doc)
    (response/response {:status true})))

(defn update-service-provider-profile
  [service-provider-id {:keys [name email age gender district pin-code latitude longitude]}]
  (let [id (object-id service-provider-id)
        coll "serviceProviders"
        doc {$set {:name name
                   :email email
                   :age (str->int age)
                   :gender gender
                   :district district
                   :pin-code (str->int pin-code)
                   :latitude latitude
                   :longitude longitude}}]
    (command/update-doc-by-id coll id doc)
    (response/response {:status true})))

(defn update-my-profile
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          params (:params req)
          coll "authTokens"
          res (-> (query/retreive-one-by-custom-key-value coll :token token))
          user-id (:user-id res)
          user-type (:user-type res)]
      (cond
        (= "customer" user-type) (update-customer-profile user-id params)
        (= "service-provider" user-type) (update-service-provider-profile user-id params)))))
