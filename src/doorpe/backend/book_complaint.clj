(ns doorpe.backend.book-complaint
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request]]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.ingestion :as insert]))

(defn book-complaint
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          params (:params req)
          coll "authTokens"
          res (-> (query/retreive-one-by-custom-key-value coll :token token))
          user-id (:user-id res)
          user-type (:user-type res)
          service-id (object-id (:service-id params))
          email (:email params)
          user-name (:user-name params)
          description (:description params)
          id (object-id)

          doc {:_id id
               :user-id user-id
               :user-name user-name
               :user-type user-type
               :service-id service-id
               :email email
               :description description
               :addressed false}

          res (insert/doc "complaints" doc)]
      (if res
        (response/response {:status true})
        (response/response {:status false})))))