(ns doorpe.backend.admin-service-requests
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.server.file-upload :refer [file-upload]]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$push]]
            [clojure.java.io :as io]
            [doorpe.backend.db.ingestion :as insert]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.command :as command]))

(defn approve-service
  [_id]
  (let [from-coll "requestsForCertifiedServices"
        to-coll "serviceProviders"

        requested-service (query/retreive-by-id from-coll _id)

        request-id (str (:_id requested-service))
        servcie-provider-id (str (:service-provider-id requested-service))
        service-info (:service-info requested-service)
        doc {$push {:services-providing service-info}}

        update-res (command/update-by-id to-coll servcie-provider-id doc)
        remove-res (command/remove-by-id from-coll request-id)]

    (if (and update-res remove-res)
      (response/response {:status true})
      (response/response {:status false}))))

(defn reject-service
  [_id]
  (let [coll "requestsForCertifiedServices"
        file-name (-> (query/retreive-by-id coll _id)
                      :service-info
                      :certificate-img)
        file (and file-name
                  (io/resource (str "img/" file-name)))
        del-file (io/delete-file file true)

        res (command/remove-by-id coll _id)]
    (if res
      (response/response {:status true})
      (response/response {:status false}))))

(defn admin-service-requests
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    (let [params (:params req)
          action (:action params)
          _id (:_id params)]
      (cond
        (= "approve-service" action) (approve-service _id)
        (= "reject-service" action) (reject-service _id)))))