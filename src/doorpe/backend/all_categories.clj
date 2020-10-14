(ns doorpe.backend.all-categories
  (:require [ring.util.response :as response]
            [doorpe.backend.db.query :as query]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.data.codec.base64 :as b64]))

; https://stackoverflow.com/questions/11825444/clojure-base64-encoding/16781372
; https://github.com/cloojure/tupelo
(defn convert-image-from-filesystem
  "Function to convert image from filepath to base64"
  [image-path]
  (->> image-path
       (clojure.java.io/file)
       (org.apache.commons.io.FileUtils/readFileToByteArray)
       (.encodeToString (java.util.Base64/getEncoder))))

(defn transform-img
  [category]
  (let [file-name (:img category)
        file-extension (last (string/split file-name #"\."))
        file (io/resource (str "img/" file-name))
        base64-str (convert-image-from-filesystem file)
        src (str "data:image/" file-extension ";base64," base64-str)]
    (assoc category :img src)))

(defn all-categories
  [req]
  (let [coll "categories"
        categories (pmap transform-img (query/retreive-coll coll))]
    (response/response categories)))