(ns doorpe.backend.db.query
  (:require [monger.collection :as mc]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.db :refer [get-db-ref]]
            [doorpe.backend.util :refer [exists-and-not-empty?
                                         valid-hexa-string?
                                         doc-object-id->str
                                         docs-object-id->str]]))

(def ^:private db (get-db-ref))

(defn customers
  []
  (let [coll "customers"
        db db]
    (if (exists-and-not-empty? db coll)
      (-> (mc/find-maps db coll)
          docs-object-id->str)
      nil)))

(defn customer
  [id]
  (let [coll "customers"]
    (if (and (exists-and-not-empty? db coll) (valid-hexa-string? id))
      (->> (object-id id)
           (mc/find-map-by-id db coll)
           doc-object-id->str)
      nil)))