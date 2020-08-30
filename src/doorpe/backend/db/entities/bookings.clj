(ns doorpe.backend.db.entities.bookings
  (:require [doorpe.backend.util :refer [bson-object-id]]))


(def entity-name "bookings")

(def entity-vec [{:pending [{:_id (bson-object-id)
                             :customer-id (bson-object-id "5f48ab6b799d90710eaea363")
                             :provider-id (bson-object-id "5f48ab6b799d90710eaea366")
                             :service-id (bson-object-id "5f48ab6b799d90710eaea366")
                             :booking-on "date"
                             :service-on "date"
                             :service-time "time"}
                            {:_id (bson-object-id)
                             :customer-id (bson-object-id "5f48ab6b799d90710eaea363")
                             :provider-id (bson-object-id "5f48ab6b799d90710eaea366")
                             :service-id (bson-object-id "5f48ab6b799d90710eaea366")
                             :booking-on "date"
                             :service-on "date"
                             :service-time "time"}]
                  :accepted [{:_id (bson-object-id)
                              :customer-id (bson-object-id "5f48ab6b799d90710eaea363")
                              :provider-id (bson-object-id "5f48ab6b799d90710eaea366")
                              :service-id (bson-object-id "5f48ab6b799d90710eaea366")
                              :booking-on "date"
                              :service-on "date"
                              :service-time "time"}]
                  :canceled [{:_id (bson-object-id)
                              :customer-id (bson-object-id "5f48ab6b799d90710eaea363")
                              :provider-id (bson-object-id "5f48ab6b799d90710eaea366")
                              :service-id (bson-object-id "5f48ab6b799d90710eaea366")
                              :booking-on "date"
                              :service-on "date"
                              :service-time "time"
                              :cancelation-date "date"
                              :cancelation-reason "......"}]
                  :rejected [{:_id (bson-object-id)
                              :customer-id (bson-object-id "5f48ab6b799d90710eaea363")
                              :provider-id (bson-object-id "5f48ab6b799d90710eaea366")
                              :service-id (bson-object-id "5f48ab6b799d90710eaea366")
                              :booking-on "date"
                              :service-on "date"
                              :service-time "time"
                              :cancelation-date "date"
                              :cancelation-reason "......"}]
                  :fulfilled [{:_id (bson-object-id)
                               :customer-id (bson-object-id "5f48ab6b799d90710eaea363")
                               :provider-id (bson-object-id "5f48ab6b799d90710eaea366")
                               :service-id (bson-object-id "5f48ab6b799d90710eaea366")
                               :booking-on "date"
                               :service-on "date"
                               :service-time "time"
                               :review-id (bson-object-id "5f48ab6b799d90710eaea366")}]}])