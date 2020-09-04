(ns doorpe.backend.db.ingestion
  (:require [monger.collection :as mc]
            [doorpe.backend.db.db :as db]))

(def ^:private db-ref (db/get-db-ref))

(defn doc
  "Accepts a map"
  [coll doc]
  (mc/insert db-ref coll doc))

(defn docs
  "Accepts a vector of maps"
  [coll docs]
  (mc/insert-batch db-ref coll docs))
