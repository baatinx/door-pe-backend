(ns doorpe.backend.db.entities.customers
  (:require [monger.uitl :refer [object-id]]))
(def entity-name "customers")

(def entity-vec [{:_id (object-id)
                  :name "Hammad Mir"
                  :contact 70067878963
                  :email "hammad@gmail.com"
                  :user-type "customer"
                  :password-digest "password-digest"
                  :password-hint "my password hint"
                  :type "admin"
                  :age 21
                  :gender "m"
                  :img "./img/...."
                  :district "Srinagar"
                  :address "House no. 105, Solina Payeen, opp. petrol pump"
                  :pin-code 190008
                  :landmark "near Al-hadees masjid"
                  :coordinates {:home {:latitude 34.064621 :longitude 74.818832}
                                :custom {:latitude 1.302030 :longitude 103.862991}}}
                 {:_id (object-id)
                  :name "Seerat Mir"
                  :contact 700678787878
                  :email "seerat@gmail.com"
                  :password "my-password"
                  :hint "my password hint"
                  :type "admin"
                  :age 20
                  :gender "f"
                  :img "./img/...."
                  :district "Srinagar"
                  :address "Rajbagh"
                  :landmark "Opposite Post office"
                  :pin-code 190009
                  :coordinates {:home {:latitude 34.064621 :longitude 74.818832}
                                :custom {:latitude 1.302030 :longitude 103.862991}}}])