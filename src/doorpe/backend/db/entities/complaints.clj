(ns doorpe.backend.db.entities.complaints
  (:require [monger.uitl :refer [object-id]]))

(def entity-name "complaints")

(def entity-vec [{:_id (object-id)
                  :user-id (object-id "5f48ab6b799d90710eaea366")
                  :user-type "customer"
                  :email "email"
                  :description "I recently......"
                  :addressed false}])
