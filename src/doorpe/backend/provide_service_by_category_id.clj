(ns doorpe.backend.provide-service-by-category-id
  (:require [ring.util.response :as response]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$nin]]
            [doorpe.backend.util :refer [img->base64 extract-token-from-request]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.db.util :refer [token->token-details]]
            [doorpe.backend.db.query :as query]))

(defn provide-service-by-category-id
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          {user-id :user-id user-type :user-type} (token->token-details token)

          coll "serviceProviders"
          services-already-providing (pmap #(:service-id %)
                                           (:services-providing (query/retreive-by-id coll user-id)))

          category-id   (-> req
                            :params
                            :category-id
                            object-id)
          ref {:category-id category-id
               :_id {$nin (vec services-already-providing)}}

          services (pmap #(img->base64 :img %)
                         (query/retreive-all-by-custom-ref "services" ref))]
      (response/response services))))