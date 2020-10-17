(ns doorpe.backend.all-categories
  (:require [ring.util.response :as response]
            [doorpe.backend.util :refer [img->base64]]
            [doorpe.backend.db.query :as query]))

(defn all-categories
  [req]
  (let [coll "categories"
        categories (pmap #(img->base64 :img %)
                         (query/retreive-coll coll))]
    (response/response categories)))