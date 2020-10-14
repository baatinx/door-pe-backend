(ns doorpe.backend.admin-add
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.server.file-upload :refer [file-upload]]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.ingestion :as insert]))

(defn add-category
  [{:keys [name description my-file]}]
  (let [id (object-id)
        coll "categories"
        {file-status :file-status file-name :file-name} (file-upload my-file)
        doc (and file-status
                 {:_id id
                  :name name
                  :description description
                  :img file-name})
        res  (and doc
                  (insert/doc coll doc))]
    (if res
      (response/response {:insert-status true})
      (response/response {:insert-status false}))))

(defn add-service
  [{:keys [name category-id charge-type critical-service description]}]
  (let [id (object-id)
        coll "services"
        is-critical-service? (if (= "true" critical-service) true false)
        doc {:_id id
             :name name
             :category-id (object-id category-id)
             :charge-type charge-type
             :critical-service is-critical-service?
             :description description}]
    (if  (insert/doc coll doc)
      (response/response {:insert-status true})
      (response/response {:insert-status false}))))

(defn admin-add
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    (let [params (:params req)
          add-what (:add-what params)]
      (cond
        (= "category" add-what) (add-category params)
        (= "service" add-what) (add-service params)))))