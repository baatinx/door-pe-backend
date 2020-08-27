(ns doorpe.backend.db.ingestion
  (:require [monger.collection :as mc]
            [doorpe.backend.db.db :as db]))

(def ^:private db-ref (db/get-db-ref ))

(defn  category
  [doc]
  (mc/insert db-ref "category" doc))

(defn category-batch
  [docs]
  (mc/insert-batch db-ref "category" docs))
