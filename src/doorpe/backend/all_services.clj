(ns doorpe.backend.all-services
  (:require [ring.util.response :as response]
            [doorpe.backend.db.query :as query]))

(defn all-services
  [req]
  (let [coll "services"
        services (query/retreive-coll coll)]
    (response/response services)))