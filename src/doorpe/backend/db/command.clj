(ns doorpe.backend.db.command
  (:require [monger.collection :as mc]
            [doorpe.backend.db.db :refer [get-db-ref]]
            [monger.util :refer [object-id]]
            [doorpe.backend.util :refer [exists-and-not-empty?
                                         valid-hexa-string?
                                         valid-coll-name?]]))

(def ^:private db-ref (get-db-ref))

(defn remove-by-id
  [coll id]
  (let [db db-ref
        res (and (valid-coll-name? coll)
                 (valid-hexa-string? id)
                 (exists-and-not-empty? db coll))]
    (if res
      (mc/remove-by-id db coll (object-id id)))))

(defn remove-by-key-value
  [coll key value]
  (let [db db-ref
        condition {key value}]
    (mc/remove db coll condition)))

(defn update-doc
  [coll conditions doc]
  (let [db db-ref]
    (mc/update db coll conditions doc)))

(defn update-by-id
  [coll id doc]
  (let [db db-ref
        res (and (valid-coll-name? coll)
                 (valid-hexa-string? id)
                 (exists-and-not-empty? db coll))]
    (if res
      (mc/update-by-id db coll (object-id id) doc))))
