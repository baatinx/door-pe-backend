(ns doorpe.backend.server.handler
  (:require [clojure.string :as string]
            [ring.util.response :as response]
            [monger.util :refer [object-id]]
            [muuntaja.core :as m]
            [jsonista.core :as j]
            [clj-http.client :as http]
            [buddy.hashers :as hashers]
            [doorpe.backend.util :refer [str->int]]
            [doorpe.backend.db.ingestion :as insert]
            [doorpe.backend.db.query :as query]
            [doorpe.backend.server.authentication :as auth]
            [buddy.auth :refer [authenticated? throw-unauthorized]]))

(defn inspect-request-map
  [req]
  (response/response (str req)))

(defn customer-show-all
  [req]
  (response/response (query/retreive-all "customers")))

(defn customer-dashboard
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    {:status 200 :body  {:status "Logged" :message (str "customer-dashboard"
                                                        (:identity req))}}))
; curl -H "Authorization: Token MPMQndaYXv0HRETAtvxGmPkkqPX593nOONsaSZA+pic=" http://localhost:7000/customer/dashboard

; curl -H "Authorization: Token /VwzFIKzVgFOZUeykendtzbKRS/uUvBtfF+LjYB0XRI=" http://localhost:7000/customer/dashboard

(defn register-as-customer!
  [req]
  ;; check for -  phone no already exists
  (let [id (object-id)
        user-type "customer"
        {:keys [name contact district address password]} (:params req)
        password-digest (hashers/encrypt password)
        doc {:_id id
             :name name
             :contact (str->int contact)
             :district district
             :address address
             :password-digest password-digest
             :user-type user-type}]
    (insert/doc "customers" doc)
    (response/response {:insert-status true})))


; (register-as-customer! {:params {:name "Danish Hanief"
                                ;  :contact 1234567890
                                ;  :district "Srinagar"
                                ;  :address "Natipora"
                                ;  :password "my-password"}})

;; curl -X POST -d "name=Danish&contact=1234567890&district=Srinagar&address=Natipora&password=my-password" "http://localhost:7000/register-as-customer"

(defn register-as-service-provider!
  [req]
  (let [id (object-id)
        {:keys [name contact district address]} (-> req
                                                    :params)
        doc {:_id id
             :name name
             :contact contact
             :district district
             :address address}]
    (insert/doc "providers" doc)))

(defn send-otp
  [req]
  (let [to-number (-> req
                      :params
                      :contact)
        otp (rand-int 999999)
        template_name "Doorpe_Registration"

        ;;https://2factor.in/CP/otp_account_list.php
        ;;https://2factor.in/API/V1/{api_key}/SMS/{phone_number}/{otp}
        ;;https://2factor.in/API/V1/{api_key}/SMS/{phone_number}/{otp}/{template_name}

        ;; api-key (slurp (io/resource "sms-api-key.txt"))
        api-key "1234-5678-1234-5678-1234-5678"

        url (str "https://2factor.in/API/V1/" api-key "/SMS/" to-number "/" otp "/" template_name)
        sms-api-response (http/get url {:throw-exceptions false})]
    (if (= 200 (:status sms-api-response))
      (merge
       (response/response {:success true :expected-otp otp}))

      (merge
      ;;  (response/response {:success false :expected-otp nil})
       (response/response {:success true :expected-otp 123456})))))

(defn login
  [req]
  (let [username (-> (get-in req [:params :username])
                     str->int)
        password (get-in req [:params :password])
        [credentials-ok? res] (auth/auth-user? username password)]
    (if credentials-ok?
      (let [{:keys [user-id user-type name address latitude longitude]} res
            token (auth/create-auth-token! user-id)]
        (response/response {:token token :user-id user-id :user-type user-type :name name :address address :latitude latitude :longitude longitude}))
      (response/response {:token nil :user-id nil :user-type nil}))))

(defn logout
  [req]
  (if-not (authenticated? req)
    (throw-unauthorized)
    (let [token (-> req
                    :headers
                    (get "authorization")
                    (string/split #"\s")
                    second)
          loggout? (auth/logout token)]
      (if loggout?
        (response/response {:logout true})
        (response/response {:logout false})))))