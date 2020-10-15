(ns doorpe.backend.register
  (:require [ring.util.response :as response]
            [monger.util :refer [object-id]]
            [buddy.hashers :as hashers]
            [doorpe.backend.util :refer [str->int]]
            [doorpe.backend.server.file-upload :refer [file-upload]]
            [doorpe.backend.db.ingestion :as insert]))

(defn register-customer
  [customer params]
  (let [coll "customers"
        id (object-id)
        {:keys [name contact district address password latitude longitude my-file]} params
        {file-status :file-status file-name :file-name} (file-upload my-file)
        password-digest (and file-status
                             (hashers/encrypt password))
        doc (and password
                 {:_id id
                  :name name
                  :contact (str->int contact)
                  :district district
                  :address address
                  :password-digest password-digest
                  :user-type customer
                  :latitude latitude
                  :longitude longitude
                  :img file-name})
        res (and doc
                 (insert/doc coll doc))]
    (if res
      (response/response {:insert-status true})
      (response/response {:insert-status false}))))

(defn register-service-provider
  [service-provider params]
  (let [coll "serviceProviders"
        id (object-id)
        {:keys [name contact district password latitude longitude my-file]} params
        {file-status :file-status file-name :file-name} (file-upload my-file)
        password-digest (and file-status
                             (hashers/encrypt password))
        doc (and password-digest
                 {:_id id
                  :name name
                  :contact (str->int contact)
                  :district district
                  :password-digest password-digest
                  :user-type service-provider
                  :latitude latitude
                  :longitude longitude
                  :img file-name})
        res (and doc
                 (insert/doc coll doc))]
    (if res
      (response/response {:insert-status true})
      (response/response {:insert-status false}))))

(defn register
  [req]
  ;; TBD -  phone no already exists
  (let [params (:params req)
        user-type (:user-type params)]
    (cond
      (= "customer" user-type) (register-customer user-type params)
      (= "service-provider" user-type) (register-service-provider user-type params))))

;; curl -X POST -d "name=Danish&contact=1234567890&district=Srinagar&address=Natipora&password=my-password" "http://localhost:7000/register-as-customer"
