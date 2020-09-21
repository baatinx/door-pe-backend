(ns doorpe.backend.server.handler
  (:require [ring.util.response :as response]
            [monger.util :refer [object-id]]
            [muuntaja.core :as m]
            [jsonista.core :as j]
            [clj-http.client :as http]
            [clojure.java.io :as io]
            [doorpe.backend.db.ingestion :as insert]
            [doorpe.backend.db.query :as query]))

(defn get-params
  [req key]
  (if (and (keyword? key))
    (-> req
        :params
        key)
    nil))

(defn customers
  [req]
  (merge
   (response/response (query/retreive-all "customers"))))

(defn customer
  [req]
  (let [id (-> req
               :params
               :id)]
    (merge
     (response/response (query/retreive-by-id "customers" id)))))

(defn inspect
  [req]
  (str req))

(defn register-as-customer!
  [req]
  (let [id (object-id)
        {:keys [name contact district address password]} (-> req
                                                             :params)
        doc {:_id id
             :name name
             :contact contact
             :district district
             :address address
             :password password}]
    (insert/doc "customers" doc)
    (response/response {:insert-status true})))


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
        api-key (slurp (io/resource "sms-api-key.txt"))

        url (str "https://2factor.in/API/V1/" api-key "/SMS/" to-number "/" otp "/" template_name)
        sms-api-response (http/get url {:throw-exceptions false})]
    (if (= 200 (:status sms-api-response))
      (merge
       (response/response {:success true :expected-otp otp}))

      (merge
       (response/response {:success false :expected-otp nil})))))

;; sms-api-response - on Success
;; => {:cached nil,
;;     :request-time 1211,
;;     :repeatable? false,
;;     :protocol-version {:name "HTTP", :major 1, :minor 1},
;;     :streaming? true,
;;     :http-client
;;     #object[org.apache.http.impl.client.InternalHttpClient 0x24eb9695 "org.apache.http.impl.client.InternalHttpClient@24eb9695"],
;;     :chunked? true,
;;     :cookies
;;     {"__cfduid"
;;      {:discard false,
;;       :domain "2factor.in",
;;       :expires #inst "2020-10-21T16:56:04.000-00:00",
;;       :path "/",
;;       :secure false,
;;       :value "d826173d69432863b9f4d815b24477bed1600707364",
;;       :version 0}},
;;     :reason-phrase "OK",
;;     :headers
;;     {"Access-Control-Allow-Headers" "*",
;;      "Server" "cloudflare",
;;      "Content-Type" "application/json",
;;      "Access-Control-Allow-Origin" "*",
;;      "Connection" "close",
;;      "cf-request-id" "0553311f2a0000de6ee4a0f200000001",
;;      "Transfer-Encoding" "chunked",
;;      "Expect-CT" "max-age=604800, report-uri=\"https://report-uri.cloudflare.com/cdn-cgi/beacon/expect-ct\"",
;;      "CF-Cache-Status" "DYNAMIC",
;;      "CF-RAY" "5d6551451c5ede6e-BOM",
;;      "Access-Control-Allow-Methods" "PUT, GET, POST, DELETE, OPTIONS",
;;      "Date" "Mon, 21 Sep 2020 16:56:04 GMT",
;;      "X-Powered-By" "PHP/5.4.35"},
;;     :orig-content-encoding "gzip",
;;     :status 200,
;;     :length -1,
;;     :body "{\"Status\":\"Success\",\"Details\":\"738704be-c14e-48aa-bf1b-3fa5f852bb68\"}",
;;     :trace-redirects []}

;; sms-api-response - on Failure - Request from IP not Allowed
;; => {:cached nil,
;;     :request-time 729,
;;     :repeatable? false,
;;     :protocol-version {:name "HTTP", :major 1, :minor 1},
;;     :streaming? true,
;;     :http-client
;;     #object[org.apache.http.impl.client.InternalHttpClient 0x1f22b38f "org.apache.http.impl.client.InternalHttpClient@1f22b38f"],
;;     :chunked? true,
;;     :cookies
;;     {"__cfduid"
;;      {:discard false,
;;       :domain "2factor.in",
;;       :expires #inst "2020-10-21T16:54:45.000-00:00",
;;       :path "/",
;;       :secure false,
;;       :value "dbd973a078a89eb14915ea3de081898bf1600707285",
;;       :version 0}},
;;     :reason-phrase "Bad Request",
;;     :headers
;;     {"Access-Control-Allow-Headers" "*",
;;      "Server" "cloudflare",
;;      "Content-Type" "application/json",
;;      "Access-Control-Allow-Origin" "*",
;;      "Connection" "close",
;;      "cf-request-id" "05532feac90000de151e93a200000001",
;;      "Transfer-Encoding" "chunked",
;;      "Expect-CT" "max-age=604800, report-uri=\"https://report-uri.cloudflare.com/cdn-cgi/beacon/expect-ct\"",
;;      "CF-Cache-Status" "DYNAMIC",
;;      "CF-RAY" "5d654f57aeb0de15-BOM",
;;      "Access-Control-Allow-Methods" "PUT, GET, POST, DELETE, OPTIONS",
;;      "Date" "Mon, 21 Sep 2020 16:54:46 GMT",
;;      "X-Powered-By" "PHP/5.4.35"},
;;     :orig-content-encoding nil,
;;     :status 400,
;;     :length -1,
;;     :body "{\"Status\":\"Error\",\"Details\":\"Request Rejected - IP Not Allowed\"}",
;;     :trace-redirects []}