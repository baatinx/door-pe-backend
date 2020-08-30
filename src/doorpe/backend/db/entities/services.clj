(ns doorpe.backend.db.entities.services
  (:require [doorpe.backend.util :refer [bson-object-id]]))

(def entity-name "services")

(def entity-vec [{:_id (bson-object-id)
                  :name "Washing Machine Repair"
                  :charge-type "variable"
                  :category "Home Appliances/Hardware Repair"
                  :critical-service false
                  :desc "Problem with your Washing Machine? Not a big deal now, get expert within no time ... we fix all types of Washing maching(Fully-automatic, semi-automatic) from top brands like svmsung, LG, Godrej, whirlpool"}
                 {:_id (bson-object-id)
                  :name "Electrician"
                  :charge-type "variable"
                  :category "..."
                  :critical-service false
                  :desc "..."}
                 {:_id (bson-object-id)
                  :name "Hair Cutting for Mens"
                  :charge-type "Fixed"
                  :category "Beauty and Personal Care"
                  :critical-service false
                  :desc "..."}
                 {:_id (bson-object-id)
                  :name "Hair Cut for Girls"
                  :charge-type "Fixed"
                  :category "Beauty and Personal Care"
                  :critical-service false
                  :desc "..."}
                 {:_id (bson-object-id)
                  :name "Hair Coloring for Girls"
                  :charge-type "Fixed"
                  :category "Beauty and Personal Care"
                  :critical-service false
                  :desc "..."}
                 {:_id (bson-object-id)
                  :name "Water Geyser Repair"
                  :charge-type "variable"
                  :category ""
                  :critical-service false
                  :desc "..."}
                 {:_id (bson-object-id)
                  :name "Water Geyser Cleaning"
                  :charge-type "Fixed"
                  :category "Cleaning & Disinfection"
                  :critical-service false
                  :desc "..."}
                 {:_id (bson-object-id)
                  :name "Water Purifier Repair"
                  :charge-type "variable"
                  :category ""
                  :critical-service false
                  :desc "..."}])