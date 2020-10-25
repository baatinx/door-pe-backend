(ns doorpe.backend.revenue-generated
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details charges-per-booking]]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.query :as query]))

(defn revenue-generated
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          {user-id :user-id user-type :user-type} (token->token-details token)
          coll "bookings"
          key :status
          value "accepted"
          total-bookings-count (and (= "admin" user-type)
                                    (query/count-by-custom-key-value coll key value))

          charges-per-booking (charges-per-booking)
          total-revenue-generated (and (pos? total-bookings-count)
                                       (pos? charges-per-booking)
                                       (* total-bookings-count charges-per-booking))
          payments (and
                    pos?
                    (query/retreive-coll "payments"))

          total-amount-received (if payments
                                  (reduce (fn [acc payment]
                                            (+ acc (:amount payment)))
                                          0
                                          payments)
                                  0)]
      (response/response {:total-bookings-count total-bookings-count
                          :charges-per-booking charges-per-booking
                          :total-revenue-generated total-revenue-generated
                          :payments payments
                          :total-amount-received total-amount-received}))))