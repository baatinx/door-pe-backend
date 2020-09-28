(ns doorpe.backend.db.entities.reviews
  (:require [monger.uitl :refer [object-id]]))

(def entity-name "reviews")

(def entity-vec [{:_id (object-id )
                  :service-id (object-id "5f48ab6b799d90710eaea366")
                  :service-service-provider-id (object-id "5f48ab6b799d90710eaea311")
                  :review [{:customer-id (object-id "5f48ab6b799d90710eaea366")
                            :response "I'm 100 percent satisfied by the service ......."
                            :stars 5}
                           {:customer-id (object-id "5f48ab6b799d90710eaea366")
                            :response "Amazing..........."
                            :stars 4}
                           {:customer-id (object-id "5f48ab6b799d90710eaea366")
                            :response "Disappointed"
                            :stars 1}
                           {:customer-id (object-id "5f48ab6b799d90710eaea366")
                            :response "Highly......."
                            :stars 3}]}])