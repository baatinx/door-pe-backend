(ns doorpe.backend.db.entities.customers
  (:import [org.bson.types ObjectId]))

(def entity-name "customers")

(def entities-vec [{:_id (ObjectId. )
                    :name "Hammad Mir"
                    :contact 70067878963
                    :email "hammad@gmail.com"
                    :age 21
                    :gender "m"
                    :img "./img/...."
                    :District "Srinagar"
                    :city "Srinagar"
                    :address "House no. 105, Solina Payeen, opp. petrol pump"
                    :pin-code 190008
                    :landmark "near Al-hadees masjid"
                    :coordinates {:home {:latitude 34.064621 :longitude 74.818832}
                                  :custom {:latitude 1.302030 :longitude 103.862991}}}
                   {:_id (ObjectId.)
                    :name "Seerat Mir"
                    :contact 700678787878
                    :email "seerat@gmail.com"
                    :age 20
                    :gender "f"
                    :img "./img/...."
                    :District "Srinagar"
                    :city "Srinagar"
                    :address "Rajbagh"
                    :landmark "Opposite Post office"
                    :pin-code 190009
                    :coordinates {:home {:latitude 34.064621 :longitude 74.818832}
                                  :custom {:latitude 1.302030 :longitude 103.862991}}}])