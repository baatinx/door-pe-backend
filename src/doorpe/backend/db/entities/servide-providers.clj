(ns doorpe.backend.db.entities.providers
  (:require [monger.uitl :refer [object-id]]))

(def entity-name "servicePoviders")

(def entity-vec [{:_id (object-id)
                  :name "Asif Gulzar"
                  :contact 7006696969
                  :email "asif@gmail.com"
                  :user-type "service-provider"
                  :password-digest "password-digest"
                  :password-hint "my password hint"
                  :district "pulwama"
                  :age 25
                  :gender "m"
                  :img "./img/...."
                  :pin-code 170008
                  :bio "Hi! Everyone .................."
                  :coordinates {:office {:latitude 34.064621 :longitude 74.818832}
                                :custom {:latitude nil :longitude nil}}
                  :services-providing [{:service-id (object-id "5f48ab6b799d90710eaea363")
                                        :service-charges 100
                                        :charges 200
                                        :temporary-service-not-available false
                                        :experience 10
                                        :service-intro "Hi, my name is Asif, I ....."
                                        :professional-degree-holder true
                                        :degree-desc {:title "One year Dimploma in Hair cutting"
                                                      :img "./........"}}
                                       {:service-id (object-id "5f48ab6b799d90710eae9000")
                                        :service-charges 300
                                        :charges nil
                                        :temporary-service-not-available false
                                        :experience 3
                                        :service-intro "I'm expert ....."
                                        :professional-degree-holder false}]}])