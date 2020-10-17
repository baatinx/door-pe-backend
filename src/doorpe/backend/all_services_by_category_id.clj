(ns doorpe.backend.all-services-by-category-id
  (:require [ring.util.response :as response]
            [monger.util :refer [object-id]]
            [doorpe.backend.util :refer [img->base64]]
            [doorpe.backend.db.query :as query]))

(defn all-services-by-category-id
  [req]
  (let [coll "services"
        key "category-id"
        category-id   (-> req
                          :params
                          :category-id
                          object-id)
        services (pmap #(img->base64 :img %)
                       (query/retreive-all-by-custom-key-value coll key category-id))]
    (response/response services)))