(ns doorpe.backend.util
  (:require [monger.collection :as mc]
            [monger.util :refer [get-id]]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as string])
  (:import [org.bson.types ObjectId]))

(defn exists-and-not-empty?
  "Return true if collection exists and is not empty"
  [db coll]
  (and
   (mc/exists? db coll)
   (not (mc/empty? db coll))))

(defn valid-hexa-string?
  [s]
  (org.bson.types.ObjectId/isValid s))

(defn bson-object-id->str
  ([]
   (-> (ObjectId.)
       .toString))

  ([object-id]
   (.toString object-id)))

(defn coll-exists?
  [db coll]
  (mc/exists? db coll))

(defn doc-object-id->str
  "Accepts a map"
  [doc]
  (if (contains? doc :_id)
    (let [id (get-id doc)
          hexa-string (str id)]
      (and (valid-hexa-string? hexa-string) (assoc doc :_id hexa-string)))
    doc))

(defn docs-object-id->str
  "Accepts a vector of maps"
  [docs]
  (pmap #(doc-object-id->str %)
        docs))

(defn doc-custom-object-id->str
  "Accepts a map"
  [doc key]
  (if (contains? doc key)
    (let [id (get doc key)
          hexa-string (str id)]
      (and (valid-hexa-string? hexa-string) (assoc doc key hexa-string)))
    doc))

(defn docs-custom-object-id->str
  "Accepts a vector of maps"
  [docs key]
  (pmap #(doc-custom-object-id->str % key)
        docs))

(defn valid-coll-name?
  "mongodb valid coll name, check for name starts with, whether contain symbol.... - pending"
  [coll]
  (and (instance? String coll)))

(defn str->int
  [s]
  (if (instance? String s)
    (edn/read-string s)
    s))

(defn extract-token-from-request
  [req]
  (-> req
      :headers
      (get "authorization")
      (string/split #"\s")
      last))

; https://stackoverflow.com/questions/11825444/clojure-base64-encoding/16781372
; https://github.com/cloojure/tupelo
(defn convert-image-from-filesystem
  "Function to convert image from filepath to base64"
  [image-path]
  (->> image-path
       (clojure.java.io/file)
       (org.apache.commons.io.FileUtils/readFileToByteArray)
       (.encodeToString (java.util.Base64/getEncoder))))

(defn img->base64
  [doc]
  (let [file-name (:img doc)
        file-extension (and file-name (last (string/split file-name #"\.")))
        file (and file-name (io/resource (str "img/" file-name)))
        base64-str (and file (convert-image-from-filesystem file))
        src (format "data:image/%s;base64,%s" file-extension base64-str)]
    (assoc doc :img src)))
