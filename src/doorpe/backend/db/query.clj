(ns doorpe.backend.db.query
  (:require [monger.collection :as mc]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.db :refer [get-db-ref]]
            [doorpe.backend.util :refer [exists-and-not-empty?
                                         valid-hexa-string?
                                         doc-object-id->str
                                         docs-object-id->str
                                         valid-coll-name?]]))

(def ^:private db-ref (get-db-ref))

(defn retreive-coll
  [coll]
  (let [db db-ref]
    (if (and (valid-coll-name? coll)
             (exists-and-not-empty? db coll))
      (-> (mc/find-maps db coll)
          docs-object-id->str)
      nil)))

(defn retreive-by-id
  [coll id]
  (let [db db-ref]
    (if (and (valid-coll-name? coll)
             (valid-hexa-string? id)
             (exists-and-not-empty? db coll))
      (->> (object-id id)
           (assoc {} :_id)
           (mc/find-one-as-map db coll)
           doc-object-id->str)
      nil)))

(defn retreive-one-by-custom-key-value
  [coll key value]
  (let [db db-ref
        ref {key value}]
    (if (and (valid-coll-name? coll)
             (exists-and-not-empty? db coll))
      (->> ref
           (mc/find-one-as-map db coll)
           doc-object-id->str)
      nil)))

(defn retreive-all-by-custom-key-value
  [coll key value]
  (let [db db-ref
        ref {key value}]
    (if (and (valid-coll-name? coll)
             (exists-and-not-empty? db coll))
      (->> ref
           (mc/find-maps db coll)
           docs-object-id->str)
      nil)))

(defn retreive-one-by-custom-ref
  [coll ref]
  (let [db db-ref]
    (if (and (valid-coll-name? coll)
             (exists-and-not-empty? db coll))
      (->> (mc/find-one-as-map db coll ref)
           doc-object-id->str)
      nil)))

(defn retreive-all-by-custom-ref
  [coll ref]
  (let [db db-ref]
    (if (and (valid-coll-name? coll)
             (exists-and-not-empty? db coll))
      (->> (mc/find-maps db coll ref)
           docs-object-id->str)
      nil)))

(defn count-by-custom-ref
  [coll ref]
  (let [db db-ref]
    (if (and (valid-coll-name? coll)
             (exists-and-not-empty? db coll))
      (mc/count db coll ref)
      0)))

(defn count-by-custom-key-value
  [coll key value]
  (let [db db-ref
        ref {key value}]
    (if (and (valid-coll-name? coll)
             (exists-and-not-empty? db coll))
      (mc/count db coll ref)
      0)))
