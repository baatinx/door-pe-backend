(ns doorpe.backend.db.command
  (:require [monger.collection :as mc]
            [doorpe.backend.db.db :refer [get-db-ref]]))

(def ^:private db-ref (get-db-ref))

(defn delete-all-by-key-value
  [coll key value]
  (let [db db-ref
        condition {key value}]
    (mc/remove db coll condition)))