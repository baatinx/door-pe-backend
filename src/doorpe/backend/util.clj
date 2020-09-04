(ns doorpe.backend.util
  (:require [monger.collection :as mc])
  (:import [org.bson.types ObjectId]))

(defn bson-object-id
  ([]
   (ObjectId.))

  ([hexa-string]
   (ObjectId. hexa-string)))

(defn bson-object-id->str
  ([]
   (-> (ObjectId.)
       .toString))

  ([object-id]
   (.toString object-id)))

(defn coll-exists?
  [db coll]
  (mc/exists? db coll))

(defn get-id
  [doc]
  (:_id doc))

(defn doc-id-timestamp-stuff->str
  "Accepts a map"
  [doc]
  (let [bson-obj (get-id doc)
        equivalent-hexa-string (bson-object-id->str bson-obj)
        key :_id]
    (assoc doc key equivalent-hexa-string)))

(defn docs-id-timestamp-stuff->str
  "Accepts a vector of maps"
  [docs]
  (pmap #(doc-id-timestamp-stuff->str %)
        docs))