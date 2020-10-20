(ns doorpe.backend.reject-service-request
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [clojure.java.io :as io]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.command :as command]))

(defn reject-service-request
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    (let [_id (-> req
                  :params
                  :_id)
          coll "serviceRequests"
          file-name (-> (query/retreive-by-id coll _id)
                        :service-info
                        :certificate-img)
          file (and file-name
                    (io/resource (str "img/" file-name)))
          del-file (io/delete-file file true)

          res (command/remove-by-id coll _id)]
      (if res
        (response/response {:status true})
        (response/response {:status false})))))

