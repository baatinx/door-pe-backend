(ns doorpe.backend.db.entities.complaints
  (:require [monger.uitl :refer [object-id]]))

(def entity-name "complaints")

(def entity-vec [{:_id (object-id )
                  :customer-id (object-id "5f48ab6b799d90710eaea366")
                  :category-id (object-id "5f48ab6b799d90710eaea311")
                  :complaint "I recently......"
                  :addressed false}])
