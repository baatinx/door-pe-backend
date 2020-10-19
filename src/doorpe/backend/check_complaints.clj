(ns doorpe.backend.check-complaints
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details]]
            [doorpe.backend.db.query :as query]))

(defn check-complaints
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    (let [token (extract-token-from-request req)
          user-type (-> token
                        token->token-details
                        :user-type)
          coll "complaints"
          key :addressed
          val false
          complaints (and (= user-type "admin")
                          (query/retreive-all-by-custom-key-value coll key val))]
      (response/response complaints))))
