(ns doorpe.backend.admin-edit
  (:require [ring.util.response :as response]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.ingestion :as insert]))

(defn edit-category
  [{:keys [name description]}]
  (let [id (object-id)
        coll "categories"
        doc {:_id id
             :name name
             :description description}]
    (if  (insert/doc coll doc)
      (response/response {:insert-status true})
      (response/response {:insert-status false}))))

(defn edit-service
  [{:keys [name category-id charge-type critical-service description]}]
  (let [id (object-id)
        coll "services"
        doc {:_id id
             :name name
             :category-id (object-id category-id)
             :charge-type charge-type
             :critical-service critical-service
             :description description}]
    (if  (insert/doc coll doc)
      (response/response {:insert-status true})
      (response/response {:insert-status false}))))

(defn admin-edit
  [req]
  (let [add-what (-> req
                     :params
                     :add-what)]
    (cond
      (= "category" add-what) (edit-category (:params req))
      (= "service" add-what) (edit-service (:params req)))))