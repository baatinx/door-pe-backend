(ns doorpe.backend.db.entities.providers
  (:import [org.bson.types ObjectId]))

(def entity-name "providers")

(def entities-vec [{:_id (ObjectId.)
                    :name "Asif Gulzar"
                    :contact 7006696969
                    :email "asif@gmail.com"
                    :age 25
                    :gender "m"
                    :img "./img/...."
                    :District "Pulwama"
                    :city "Pulwama"
                    :pin-code 170008
                    :address "Awatipora, near main Bus Stop"
                    :landmark nil
                    :coordinates {:office {:latitude 34.064621 :longitude 74.818832}
                                  :custom {:latitude nil :longitude nil}}
                    :services-providing [{:s-id (ObjectId. "5f48ab6b799d90710eaea363")
                                          :s-charges 100
                                          :charges 200
                                          :experience 10
                                          :s-intro "Hi, my name is Asif, I ....."
                                          }]}])