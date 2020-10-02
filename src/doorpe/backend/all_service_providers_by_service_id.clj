(ns doorpe.backend.all-service-providers-by-service-id
  (:require [ring.util.response :as response]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.util :refer [doc-custom-object-id->str]]))

(defn get-required-service-only
  [service-id {:keys [_id name contact district address services-providing]}]
  (let [required-service (->> services-providing
                              (filter #(= service-id (:service-id %))))
        service (doc-custom-object-id->str (first required-service) :service-id)
        service-charges (:service-charges service)
        charges (:charges service)
        experience (:experience service)
        service-intro (:service-intro service)
        professional-degree-holder (:professional-degree-holder service)]
    {:_id _id
     :name name
     :contact contact
     :district district
     :address address
     :service-charges service-charges
     :charges charges
     :experience experience
     :service-intro service-intro
     :professional-degree-holder professional-degree-holder}))

(defn all-service-providers-by-service-id
  [req]
  (let [coll "serviceProviders"
        key "services-providing.service-id"
        service-id   (-> req
                         :params
                         :service-id
                         object-id)
        service-providers (query/retreive-all-by-custom-key-value coll key service-id)
        res (vec (pmap #(get-required-service-only service-id %) service-providers))]
    (response/response res)))