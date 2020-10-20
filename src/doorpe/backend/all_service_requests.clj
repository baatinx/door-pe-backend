(ns doorpe.backend.all-service-requests
  (:require [ring.util.response :as response]
            [monger.util :refer [object-id]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [img->base64 docs-custom-object-id->str doc-custom-object-id->str]]
            [doorpe.backend.db.query :as query]))

(defn all-service-requests
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    (let [coll "serviceRequests"
          service-requests (-> (query/retreive-coll coll)
                               (docs-custom-object-id->str :service-provider-id))

          res (pmap (fn [request] (let [id (:_id request)
                                        service-provider-id (:service-provider-id request)
                                        service-id (-> request
                                                       :service-info
                                                       :service-id
                                                       str)

                                        service-provider-name (-> (query/retreive-by-id "serviceProviders" service-provider-id)
                                                                  :name)

                                        service-name (-> (query/retreive-by-id "services" service-id)
                                                         :name)

                                        service-info (:service-info request)

                                        transformed-service-info (->> (doc-custom-object-id->str service-info :service-id)
                                                                      (img->base64 :certificate-img))]
                                    {:_id id
                                     :service-provider-name service-provider-name
                                     :service-name service-name
                                     :service-info transformed-service-info}))
                    service-requests)]
      (response/response res))))
