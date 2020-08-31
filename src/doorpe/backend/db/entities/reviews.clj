(ns doorpe.backend.db.entities.reviews
  (:require [doorpe.backend.util :refer [bson-object-id]]))

(def entity-name "reviews")

(def entity-vec [{:_id (bson-object-id )
                  :service-id (bson-object-id "5f48ab6b799d90710eaea366")
                  :provider-id (bson-object-id "5f48ab6b799d90710eaea311")
                  :review [{:customer-id (bson-object-id "5f48ab6b799d90710eaea366")
                            :response "I'm 100 percent satisfied by the service ......."
                            :stars 5}
                           {:customer-id (bson-object-id "5f48ab6b799d90710eaea366")
                            :response "Amazing..........."
                            :stars 4}
                           {:customer-id (bson-object-id "5f48ab6b799d90710eaea366")
                            :response "Disappointed"
                            :stars 1}
                           {:customer-id (bson-object-id "5f48ab6b799d90710eaea366")
                            :response "Highly......."
                            :stars 3}]}])