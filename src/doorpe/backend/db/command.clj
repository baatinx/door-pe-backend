(ns doorpe.backend.db.command
  (:require [monger.collection :as mc]
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

(update-doc "bookings" {:_id (object-id "5f70b6b2c6da85754f72b682")
                        :customer-id (object-id "5f70b82dc6da85754f72b68d")}
            {:status "pend"})

(defn update-doc-by-id
  [coll object-id doc]
  (let [db db-ref]
    (mc/update-by-id db coll object-id doc)))
