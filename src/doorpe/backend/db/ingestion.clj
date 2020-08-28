(ns doorpe.backend.db.ingestion
  (:require [monger.collection :as mc]
            [doorpe.backend.db.db :as db]))

(def ^:private db-ref (db/get-db-ref))

(defn doc
  [coll doc]
  (mc/insert db-ref coll doc))

(defn docs
  [coll docs]
  (mc/insert-batch db-ref coll docs))
