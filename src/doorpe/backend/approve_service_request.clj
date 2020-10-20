(ns doorpe.backend.approve-service-request
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [monger.operators :refer [$push]]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.db.command :as command]))

(defn approve-service-request
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    (let [_id (-> req
                  :params
                  :_id)
          from-coll "serviceRequests"
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
        (response/response {:status false})))))
