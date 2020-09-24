(ns doorpe.backend.db.db
  (:require [monger.core :as mg]
            [monger.credentials :as mcr]))

(def ^:private host "localhost")
(def ^:private port 27017)
(def ^:private db-name "doorpeDB")
(def ^:private username "mustafa-basit")
(def ^:private password "root")
(def ^:private uri (str "mongodb://" host ":" port "/" db-name ))

(defn ^:private get-conn
  []
  (mg/connect {:host host
               :port port}))

(defn get-db-ref
  []
  (mg/get-db (get-conn) db-name))

(defn ^:private get-credentials
  []
  (mcr/create username (get-db-ref ) password))

(defn get-conn-via-credentials
  []
  (mg/connect-with-credentials (get-credentials)))

(defn get-conn-via-uri
  "returns :conn and :db as map"
  []
  (mg/connect-via-uri uri))