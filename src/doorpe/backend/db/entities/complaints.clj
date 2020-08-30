(ns doorpe.backend.db.entities.complaints
  (:require [doorpe.backend.util :refer [bson-object-id]]))

(def entity-name "complaints")

(def entity-vec [{:_id (bson-object-id )
                  :customer-id (bson-object-id "5f48ab6b799d90710eaea366")
                  :category-id (bson-object-id "5f48ab6b799d90710eaea311")
                  :complaint "I recently......"
                  :addressed false}])
