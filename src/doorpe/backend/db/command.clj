(ns doorpe.backend.db.command
  (:require [monger.collection :as mc]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.db :refer [get-db-ref]]))

(def ^:private db-ref (get-db-ref))

(defn delete-all-by-key-value
  [coll key value]
  (let [db db-ref
        condition {key value}]
    (mc/remove db coll condition)))

(defn update-doc
  [coll conditions doc]
  (let [db db-ref]
    (mc/update db coll conditions doc)))

(defn update-doc-by-id
  [coll object-id doc]
  (let [db db-ref]
    (mc/update-by-id db coll object-id doc)))
