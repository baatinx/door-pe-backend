(ns doorpe.backend.db.query
  (:require [monger.collection :as mc]
            [doorpe.backend.db.db :refer [get-db-ref]]
            [doorpe.backend.util :refer [coll-exists? bson-object-id docs-id-timestamp-stuff->str doc-id-timestamp-stuff->str]]))

(def ^:private db (get-db-ref))

(defn customers
  []
  (let [coll "customers"]
    (if (coll-exists? db coll)
      (-> (mc/find-maps db coll)
          docs-id-timestamp-stuff->str)
      nil)))

(defn customer
  [id]
  (let [coll "customers"]
    (if (coll-exists? db coll)
      (->> (bson-object-id id)
           (mc/find-map-by-id db coll)
           doc-id-timestamp-stuff->str)
      nil)))