(ns doorpe.backend.db.entities.bookings
  (:require [monger.uitl :refer [object-id]]))

(def entity-name "bookings")

(def entity-vec [{:_id (object-id)
                  :customer-id (object-id "5f48ab6b799d90710eaea363")
                  :service-provider-id (object-id "5f48ab6b799d90710eaea366")
                  :service-id (object-id "5f48ab6b799d90710eaea366")
                  :booking-on "date"
                  :service-on "date"
                  :service-time "time"
                  :service-charges 100
                  :charges 300
                  :latitude 34.057940699999996
                  :longitude 74.8050822
                  :status "pending"}
                 {:_id (object-id)
                  :customer-id (object-id "5f48ab6b799d90710eaea363")
                  :service-provider-id (object-id "5f48ab6b799d90710eaea366")
                  :service-id (object-id "5f48ab6b799d90710eaea366")
                  :booking-on "date"
                  :service-on "date"
                  :service-time "time"
                  :service-charges 100
                  :charges 300
                  :latitude 34.057940699999996
                  :longitude 74.8050822
                  :status "accepted"}
                 {:_id (object-id)
                  :customer-id (object-id "5f48ab6b799d90710eaea363")
                  :service-provider-id (object-id "5f48ab6b799d90710eaea366")
                  :service-id (object-id "5f48ab6b799d90710eaea366")
                  :booking-on "date"
                  :service-on "date"
                  :service-time "time"
                  :service-charges 100
                  :charges 300
                  :latitude 34.057940699999996
                  :longitude 74.8050822
                  :status "canceled"
                  :cancelation-date "date"
                  :cancelation-reason "......"}
                 {:_id (object-id)
                  :customer-id (object-id "5f48ab6b799d90710eaea363")
                  :service-provider-id (object-id "5f48ab6b799d90710eaea366")
                  :service-id (object-id "5f48ab6b799d90710eaea366")
                  :booking-on "date"
                  :service-on "date"
                  :service-time "time"
                  :service-charges 100
                  :charges 300
                  :latitude 34.057940699999996
                  :longitude 74.8050822
                  :status "rejected"
                  :cancelation-date "date"
                  :rejection-reason "......"}
                 {:_id (object-id)
                  :customer-id (object-id "5f48ab6b799d90710eaea363")
                  :service-provider-id (object-id "5f48ab6b799d90710eaea366")
                  :service-id (object-id "5f48ab6b799d90710eaea366")
                  :booking-on "date"
                  :service-on "date"
                  :service-time "time"
                  :service-charges 100
                  :charges 300
                  :latitude 34.057940699999996
                  :longitude 74.8050822
                  :status "fullfilled"
                  :review-id (object-id "5f48ab6b799d90710eaea366")}])