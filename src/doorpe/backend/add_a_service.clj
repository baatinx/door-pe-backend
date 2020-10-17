(ns doorpe.backend.add-a-service
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [str->int extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details]]
            [doorpe.backend.server.file-upload :refer [file-upload]]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$push]]
            [doorpe.backend.db.command :as command]
            [doorpe.backend.db.ingestion :as insert]))

(defn register-certified-service-for-approval
  [service-provider-id user-type service-info]
  (let [coll "requestsForCertifiedServices"
        id (object-id)
        doc {:_id id
             :service-provider-id (object-id service-provider-id)
             :service-info service-info}
        res (and (= "service-provider" user-type) (insert/doc coll doc))]
    (if res
      (response/response {:status true})
      (response/response {:status false}))))

(defn add-a-service
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          {user-id :user-id user-type :user-type} (token->token-details token)
          params (:params req)
          {:keys [service-id service-charges experience service-intro]} params

          base-doc {:service-id (object-id service-id)
                    :service-charges (str->int service-charges)
                    :temporary-service-not-available false
                    :experience (str->int experience)
                    :service-intro service-intro}

          degree-doc (if (= "true" (:professional-degree-holder params))
                       (let [{:keys [degree-title my-file]} params
                             {file-status :file-status file-name :file-name} (file-upload my-file "certificate")]
                         (and file-status
                              (merge base-doc {:professional-degree-holder true
                                               :degree-title degree-title
                                               :certificate-img file-name})))

                       (merge base-doc {:professional-degree-holder false}))

          final-doc-with-charge-type (if (:charges params)
                                       (merge degree-doc {:charges (-> params
                                                                       :charges
                                                                       str->int)})
                                       degree-doc)]
      (if-not (:professional-degree-holder final-doc-with-charge-type)
        (let [coll "serviceProviders"
              doc {$push {"services-providing" final-doc-with-charge-type}}
              res (and (= "service-provider" user-type) (command/update-by-id coll user-id doc))]
          (if res
            (response/response {:status true})
            (response/response {:status false})))
        (register-certified-service-for-approval user-id user-type final-doc-with-charge-type)))))