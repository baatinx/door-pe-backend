(ns doorpe.backend.server.logout
  (:require [clojure.string :as string]
            [ring.util.response :as response]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [doorpe.backend.db.command :as command]
            [doorpe.backend.util :refer [extract-token-from-request]]))

(defn logout
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    (let [token (extract-token-from-request req)
          coll "authTokens"
          logout? (and token (command/remove-by-key-value coll :token token))]
      (if logout?
        (response/response {:logout true})
        (response/response {:logout false})))))

;; curl -X POST -d "username=1234567890&password=my-password"  -i http://localhost:7000/login
;; curl -X POST -H "Authorization: Token eSIyxdnDjNbBydG8BgRjUuRas5w4v/mFXrUbHoVCyZk=" -i http://localhost:7000/logout


