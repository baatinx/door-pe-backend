(ns doorpe.backend.db.entities.bookings
  (:import [org.bson.types ObjectId]))

(def entity-name "bookings")

(def entity-vec [{:pending [{:_id (ObjectId.)
                             :u-id (ObjectId. "5f48ab6b799d90710eaea363")
                             :p-id (ObjectId. "5f48ab6b799d90710eaea366")
                             :s-id (ObjectId. "5f48ab6b799d90710eaea366")
                             :booking-on "date"
                             :service-on "date"
                             :service-time "time"}
                            {:_id (ObjectId.)
                             :u-id (ObjectId. "5f48ab6b799d90710eaea363")
                             :p-id (ObjectId. "5f48ab6b799d90710eaea366")
                             :s-id (ObjectId. "5f48ab6b799d90710eaea366")
                             :booking-on "date"
                             :service-on "date"
                             :service-time "time"}]
                  :accepted [{:_id (ObjectId.)
                              :u-id (ObjectId. "5f48ab6b799d90710eaea363")
                              :p-id (ObjectId. "5f48ab6b799d90710eaea366")
                              :s-id (ObjectId. "5f48ab6b799d90710eaea366")
                              :booking-on "date"
                              :service-on "date"
                              :service-time "time"}]
                  :canceled [{:_id (ObjectId.)
                              :u-id (ObjectId. "5f48ab6b799d90710eaea363")
                              :p-id (ObjectId. "5f48ab6b799d90710eaea366")
                              :s-id (ObjectId. "5f48ab6b799d90710eaea366")
                              :booking-on "date"
                              :service-on "date"
                              :service-time "time"
                              :cancelation-date "date"
                              :cancelation-reason "......"}]
                  :rejected [{:_id (ObjectId.)
                              :u-id (ObjectId. "5f48ab6b799d90710eaea363")
                              :p-id (ObjectId. "5f48ab6b799d90710eaea366")
                              :s-id (ObjectId. "5f48ab6b799d90710eaea366")
                              :booking-on "date"
                              :service-on "date"
                              :service-time "time"
                              :cancelation-date "date"
                              :cancelation-reason "......"}]
                  :fulfilled [{:_id (ObjectId.)
                               :u-id (ObjectId. "5f48ab6b799d90710eaea363")
                               :p-id (ObjectId. "5f48ab6b799d90710eaea366")
                               :s-id (ObjectId. "5f48ab6b799d90710eaea366")
                               :booking-on "date"
                               :service-on "date"
                               :service-time "time"
                               :review-id (ObjectId. "5f48ab6b799d90710eaea366")}]}])