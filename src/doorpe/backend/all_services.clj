(ns doorpe.backend.all-services
  (:require [ring.util.response :as response]
            [doorpe.backend.util :refer [img->base64]]
            [doorpe.backend.db.query :as query]))

(defn all-services
  [req]
  (let [coll "services"
        services (pmap #(img->base64 :img %)
                       (query/retreive-coll coll))]
    (response/response services)))