(ns doorpe.backend.server.login
  (:require [ring.util.response :as response]
            [doorpe.backend.util :refer [str->int]]
            [doorpe.backend.server.authentication :as auth]))

(defn login
  [req]
  (let [username (-> (get-in req [:params :username])
                     str->int)
        password (get-in req [:params :password])
        [credentials-ok? res] (auth/auth-user? username password)]
    (if credentials-ok?
      (let [{:keys [user-id user-type name]} res
            token (auth/create-auth-token! user-id user-type)]
        (response/response {:token token :user-id user-id :user-type user-type :name name}))
      (response/response {:token nil :user-id nil :user-type nil}))))
