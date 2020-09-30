(ns doorpe.backend.all-services
  (:require [ring.util.response :as response]
            [doorpe.backend.db.query :as query]))

(defn all-services
  [req]
  (let [coll "services"
        categories (query/retreive-all coll)]
    (response/response categories)))