(ns doorpe.backend.register
  (:require [ring.util.response :as response]
            [monger.util :refer [object-id]]
            [buddy.hashers :as hashers]
            [doorpe.backend.util :refer [str->int]]
            [doorpe.backend.db.ingestion :as insert]))

(defn register
  [req]
  ;; check for -  phone no already exists
  (let [id (object-id)
        user-type "customer"
        {:keys [name contact district address password]} (:params req)
        password-digest (hashers/encrypt password)
        doc {:_id id
             :name name
             :contact (str->int contact)
             :district district
             :address address
             :password-digest password-digest
             :user-type user-type}]
    (insert/doc "customers" doc)
    (response/response {:insert-status true})))

;;  (register-as-customer! {:params {:name "Mustafa Basit"
                                  ;; :contact 7006787893
                                  ;; :district "Srinagar"
                                  ;; :address "Natipora"
                                  ;; :password "q"}})

;; curl -X POST -d "name=Danish&contact=1234567890&district=Srinagar&address=Natipora&password=my-password" "http://localhost:7000/register-as-customer"
