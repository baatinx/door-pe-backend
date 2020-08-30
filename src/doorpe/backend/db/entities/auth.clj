(ns doorpe.backend.db.entities.auth
  (:require [doorpe.backend.util :refer [bson-object-id]]))

(def entity-name "auth")

(def entity-vec [{:_id (bson-object-id )
                  :username 7006787878
                  :password "mypassword"
                  :hint "my password hint"
                  :type "admin"}
                 {:_id (bson-object-id )
                  :username 7006555666
                  :password "12345"
                  :hint "numeric numbers"
                  :type "provider"}
                 {:_id (bson-object-id )
                  :username 7006777777
                  :password "Hammad@123"
                  :hint "favourite name"
                  :type "customer"}])