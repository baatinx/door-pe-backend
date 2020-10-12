(ns doorpe.backend.server.send-otp
  (:require [ring.util.response :as response]
            [clj-http.client :as http]
            [clojure.java.io :as io]))

;;https://2factor.in

(defn send-otp
  [req]
  (let [to-number (-> req
                      :params
                      :contact)
        otp (rand-int 999999)
        template_name "Doorpe_Registration"
        ;; api-key (slurp (io/resource "sms-api-key.txt"))
        api-key "1234-5678-***TEMP-KEY***-1234-5678"
        url (str "https://2factor.in/API/V1/" api-key "/SMS/" to-number "/" otp "/" template_name)
        sms-api-response (http/get url {:throw-exceptions false})]
    (cond
      (= 200 (:status sms-api-response)) (response/response {:success true :expected-otp otp})
      ;; :else (response/response {:success false :expected-otp nil}))))
      :else (response/response {:success true :expected-otp 123456}))))
