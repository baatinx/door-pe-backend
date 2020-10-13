(ns doorpe.backend.add-a-service
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [str->int extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details]]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$push]]
            [doorpe.backend.db.command :as command]))

(defn add-a-service
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          {user-id :user-id user-type :user-type} (token->token-details token)
          params (:params req)
          {:keys [service-id service-charges temporary-service-not-available experience service-intro]} params

          base-doc {:service-id (object-id service-id)
                    :service-charges (str->int service-charges)
                    :temporary-service-not-available false
                    :experience (str->int experience)
                    :service-intro service-intro}

          degree-doc (if (= "true" (:professional-degree-holder params))
                       (let [{:keys [degree-title certificate]} params]
                         (merge base-doc {:professional-degree-holder true
                                          :degree-title degree-title
                                          :certificate certificate}))

                       (merge base-doc {:professional-degree-holder false}))

          final-doc-with-charge-type (if (:charges params)
                                       (merge degree-doc {:charges (-> params
                                                                       :charges
                                                                       str->int)})
                                       degree-doc)

          coll "serviceProviders"
          id (object-id user-id)
          doc {$push {"services-providing" final-doc-with-charge-type}}
          res (and (= "service-provider" user-type) (command/update-doc-by-id coll id doc))]
      (if res
        (response/response {:status true})
        (response/response {:status false})))))