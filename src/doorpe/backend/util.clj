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

(defn exists?
  [db coll]
  (mc/exists? db coll))