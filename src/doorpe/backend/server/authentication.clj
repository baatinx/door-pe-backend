(ns doorpe.backend.server.authentication
  (:require [crypto.random :refer [base64]]
            [buddy.hashers :as hashers]
            [buddy.auth.backends.token :refer [token-backend]]
            [monger.util :refer [object-id]]
            [tick.alpha.api :as time]
            [doorpe.backend.db.ingestion :as insert]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.command :as command]))

(defn auth-user?
  [username password]
  (let [coll "customers"
        key :contact
        doc (query/retreive-by-custom-key-value coll key username)]
    (if (and doc
             (:password-digest doc)
             (hashers/check password (:password-digest doc)))
      [true {:user-id (:_id doc) :user-type (:user-type doc)}]
      [false {:user-id nil :user-type nil}])))
; (auth-user? 1234567890 "my-password")


(defn create-auth-token!
  [user-id]
  (let [coll "authTokens"
        reset-user-tokens? (command/delete-all-by-key-value coll :user-id user-id)
        token (base64 32)
        id (object-id)
        doc {:_id id
             :token token
             :user-id user-id
             :exp (time/+
                   (time/now)
                   (time/new-duration 240 :minutes))}]
    (if (insert/doc coll doc)
      token
      false)))
; (create-auth-token! "5f6c28e9c6da855e0aa7be32")

(defn token-time-expired?
  [exp]
  (time/> (time/now)
          (time/instant exp)))

(defn authenticate-token
  [req token]
  (let [coll "authTokens"
        res (query/retreive-by-custom-key-value coll :token token)
        user-id (:user-id res)
        exp (:exp res)]
    (if (and res
             user-id
             exp
             (not (token-time-expired? exp)))
      user-id
      nil)))
; (authenticate-token 1 "4soMLxInrKSdE6KvPz9OnrmTB3OEEU2zI83MM3zNHaY=")


(def auth-backend (token-backend {:authfn authenticate-token}))

(defn logout
  [token]
  (let [coll "authTokens"
        logout? (command/delete-all-by-key-value coll :token token)]
    (if logout?
      true
      false)))

; curl -X POST -d "username=1234567890&password=my-password"  -i http://localhost:7000/login
; curl -X POST -H "Authorization: Token H9a+O1UxzN94cfkiOwDqllMSUii5ts9h1z+ub1oeIFE=" -i http://localhost:7000/logout