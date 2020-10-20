(ns doorpe.backend.pending-dues
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.util :refer [extract-token-from-request docs-object-id->str docs-custom-object-id->str]]
            [monger.util :refer [object-id]]
            [doorpe.backend.util :refer [img->base64]]
            [monger.operators :refer [$or]]
            [tick.core :as time]
            [doorpe.backend.util :refer [charges-per-booking]]
            [doorpe.backend.db.query :as query]))

;; (show-service-provider-my-bookings "5f73532bc6da852d7cb61a3d")
(defn pending-dues
  [req]
  (if-not (authenticated? req)
    throw-unauthorized
    (let [token (extract-token-from-request req)
          coll "authTokens"
          res (-> (query/retreive-one-by-custom-key-value coll :token token))
          user-id (:user-id res)
          user-type (:user-type res)
          ref {:_id (object-id user-id)
               :status "accepted"}

          total-bookings-count (and (= "service-provider" user-type)
                                    (query/count-by-custom-ref "bookings" ref))
          charges-per-booking (charges-per-booking)

          total-amount-due (* total-bookings-count charges-per-booking)]
      (response/response {:count count}))))