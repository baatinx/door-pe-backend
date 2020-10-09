(ns doorpe.backend.admin-add
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.ingestion :as insert]))

(defn add-category
  [{:keys [name description]}]
  (let [id (object-id)
        coll "categories"
        doc {:_id id
             :name name
             :description description}]
    (if  (insert/doc coll doc)
      (response/response {:insert-status true})
      (response/response {:insert-status false}))))

(defn add-service
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

(defn admin-add
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    (let [add-what (-> req
                       :params
                       :add-what)]
      (cond
        (= "category" add-what) (add-category (:params req))
        (= "service" add-what) (add-service (:params req))))))