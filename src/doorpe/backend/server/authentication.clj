(ns doorpe.backend.server.authentication
  (:require [crypto.random :refer [base64]]
            [buddy.hashers :as hashers]
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
  (let [reset-user-tokens? (command/delete-all-by-key-value "authTokens" :user-id user-id)
        token (base64 32)
        id (object-id)
        doc {:_id id
             :token token
             :user-id user-id
             :exp (time/+
                   (time/now)
                   (time/new-duration 30 :minutes))}]
    (if (insert/doc "authTokens" doc)
      token
      false)))

; (create-auth-token! "5f6b6272c6da8523a7609810")

(defn auth-token
  [req token]
  "TBD")
