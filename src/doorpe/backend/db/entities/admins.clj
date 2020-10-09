(ns doorpe.backend.db.entities.admins
  (:require [monger.uitl :refer [object-id]]))
(def entity-name "admins")

(def entity-vec [{:_id (object-id)
                  :name "Wasit Shafi"
                  :contact 9682648815
                  :password-digest "root"
                  :user-type "admin"}])