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