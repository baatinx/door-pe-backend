(ns doorpe.backend.server.authentication
  (:require [crypto.random :refer [base64]]
            [buddy.hashers :as hashers]
            [buddy.auth.backends.token :refer [token-backend]]
            [monger.util :refer [object-id]]
            [tick.alpha.api :as time]
            [doorpe.backend.db.ingestion :as insert]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.command :as command]))

(defn is-customer
  [username password]
  (let [coll "customers"
        key :contact
        doc (query/retreive-one-by-custom-key-value coll key username)]
    (if (and doc
             (:password-digest doc)
             (hashers/check password (:password-digest doc)))
      [true {:user-id (:_id doc) :user-type (:user-type doc) :name (:name doc)}]
      false)))

(defn is-service-provider
  [username password]
  (let [coll "serviceProviders"
        key :contact
        doc (query/retreive-one-by-custom-key-value coll key username)]
    (if (and doc
             (:password-digest doc)
             (hashers/check password (:password-digest doc)))
      [true {:user-id (:_id doc) :user-type (:user-type doc) :name (:name doc)}]
      false)))

(defn is-admin
  [username password]
  (let [coll "admins"
        key :contact
        doc (query/retreive-one-by-custom-key-value coll key username)]
    (if (and doc
             (:password-digest doc)
             (hashers/check password (:password-digest doc)))
      [true {:user-id (:_id doc) :user-type (:user-type doc) :name (:name doc)}]
      false)))

(defn auth-user?
  [username password]
  (or
   (is-customer username password)
   (is-service-provider username password)
   (is-admin username password)
   [false {:user-id nil :user-type nil}]))

(defn set-token-expiration
  []
  (time/+
   (time/now)
   (time/new-duration 240 :minutes)))

(defn create-auth-token!
  [user-id user-type]
  (let [coll "authTokens"
        reset-user-tokens? (command/remove-by-key-value coll :user-id user-id)
        token (base64 32)
        id (object-id)
        doc {:_id id
             :token token
             :user-id user-id
             :user-type user-type
             :exp (set-token-expiration)}]
    (if (insert/doc coll doc)
      token
      false)))

(defn token-expired?
  [exp]
  (time/> (time/now)
          (time/instant exp)))

(defn authenticate-token
  [req token]
  (let [coll "authTokens"
        res (query/retreive-one-by-custom-key-value coll :token token)
        user-id (:user-id res)
        exp (:exp res)]
    (if (and res
             user-id
             exp
             (not (token-expired? exp)))
      user-id
      false)))

(def auth-backend (token-backend {:authfn authenticate-token}))