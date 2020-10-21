(ns doorpe.backend.pending-dues
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details charges-per-booking]]
            [monger.util :refer [object-id]]
            [doorpe.backend.db.query :as query]))

(defn pending-dues
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          {user-id :user-id user-type :user-type} (token->token-details token)
          ref {:service-provider-id (object-id user-id)
               :status "accepted"}

          total-bookings-count (and (= "service-provider" user-type)
                                    (query/count-by-custom-ref "bookings" ref))
          charges-per-booking (charges-per-booking)

          total-amount-due (* total-bookings-count charges-per-booking)

          payments (and
                    pos?
                    (query/retreive-all-by-custom-key-value "payments" :service-provider-id (object-id user-id)))

          total-amount-paid (and payments
                                 (reduce (fn [acc payment]
                                           (+ acc (:amount payment)))
                                         0
                                         payments))]
      (response/response {:total-bookings-count total-bookings-count
                          :charges-per-booking charges-per-booking
                          :total-amount-due total-amount-due
                          :payments payments
                          :total-amount-paid total-amount-paid}))))