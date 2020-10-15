(ns doorpe.backend.server.send-email
  (:require [postal.core :refer [send-message]]
            [clojure.java.io :as io]
            [doorpe.backend.db.query :as query]))

(def email-id "contact.doorpe@gmail.com")
(def password (slurp (io/resource "email_address_password.txt")))

(def conn {:host "smtp.gmail.com"
           :ssl true
           :user email-id
           :pass password})

(defn send-email
  [to subject body]
  (let [res (and to
                 subject
                 body
                 (send-message conn {:from email-id
                                     :to to
                                     :subject subject
                                     :body [{:type "text/html"
                                             :content (str "<html><body>" body "</body></html>")}]}))]
    (if (= :SUCCESS (:error res))
      true
      false)))