(ns doorpe.backend.accept-booking
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request]]
            [doorpe.backend.db.util :refer [token->token-details]]
            [tick.core :as time]
            [monger.util :refer [object-id]]
            [monger.operators :refer [$set]]
            [doorpe.backend.db.command :as command]))

(defn accept-booking
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [booking-id (-> req
                         :params
                         :booking-id)
          token (extract-token-from-request req)

          ;; TBD
          ;; check booking status first, usertype, add a new field cancelation resaon ...

          {user-id :user-id user-type :user-type} (token->token-details token)
          coll "bookings"
          conditions {:_id (object-id booking-id)
                      :service-provider-id (object-id user-id)}
          doc {$set {:status "accepted"}}]
      (if (command/update-doc coll conditions doc)
        (response/response {:status true})
        (response/response {:status false})))))
