(ns doorpe.backend.dashboard
  (:require [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]))

(defn dashboard
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    (response/response {:status "Logged" :message "customer-dashboard"})))

;; curl -H "Authorization: Token q4t/wMl46vJROFkQnAQkcogmF5Kyn1XvVicJuZNwHBU=" http://localhost:7000/dashboard

