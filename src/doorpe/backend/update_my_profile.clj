(ns doorpe.backend.update-my-profile
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request str->int]]
            [doorpe.backend.db.util :refer [token->token-details]]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$set]]
            [doorpe.backend.db.command :as command]))

(defn update-customer-profile
  [customer-id {:keys [name email age gender district address landmark pin-code latitude longitude]}]
  (let [coll "customers"
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
    (command/update-by-id coll customer-id doc)
    (response/response {:status true})))

(defn update-service-provider-profile
  [service-provider-id {:keys [name email age gender district pin-code latitude longitude]}]
  (let [coll "serviceProviders"
        doc {$set {:name name
                   :email email
                   :age (str->int age)
                   :gender gender
                   :district district
                   :pin-code (str->int pin-code)
                   :latitude latitude
                   :longitude longitude}}]
    (command/update-by-id coll service-provider-id doc)
    (response/response {:status true})))

(defn update-my-profile
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          params (:params req)
          {user-id :user-id user-type :user-type} (token->token-details token)]
      (cond
        (= "customer" user-type) (update-customer-profile user-id params)
        (= "service-provider" user-type) (update-service-provider-profile user-id params)))))
