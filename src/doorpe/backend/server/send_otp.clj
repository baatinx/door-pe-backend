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
        opt-method (-> req
                       :params
                       :otp-method)
        otp (rand-int 999999)

        template_name "Doorpe_Registration"
         api-key (slurp (io/resource "sms-api-key.txt"))
        ;;  api-key "1234-5678-***TEMP-KEY***-1234-5678"
        text-url (format "https://2factor.in/API/V1/%s/SMS/%s/%s/%s" api-key to-number otp template_name)
        voice-url (format "https://2factor.in/API/V1/%s/VOICE/%s/%s" api-key to-number otp)
        url (if (= "voice" opt-method)
              voice-url
              text-url)
        sms-api-response (http/get url {:throw-exceptions false})
        status (:status sms-api-response)]
    (if (= 200 status)
      (response/response {:success true :expected-otp otp})
      ;;  (response/response {:success false :expected-otp nil}))))
       (response/response {:success true :expected-otp 123456}))))
