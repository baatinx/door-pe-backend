(ns doorpe.backend.db.entities.services
  (:require [monger.util :refer [object-id]]
            [doorpe.backend.db.entities.categories :refer [category-id]]))

(def entity-name "services")

(def entity-vec [{:_id (object-id)
                  :name "Washing Machine Repair"
                  :charge-type "variable"
                  :category-id (:home category-id)
                  :critical-service false
                  :desc "Problem with your Washing Machine? Not a big deal now, get expert within no time ... we fix all types of Washing maching(Fully-automatic, semi-automatic) from top brands like svmsung, LG, Godrej, whirlpool"}
                 {:_id (object-id)
                  :name "Water Geyser Cleaning"
                  :charge-type "Fixed"
                  :category-id (:clean category-id)
                  :critical-service false
                  :desc "..."}
                 {:_id (object-id)
                  :name "Hair Cutting for Mens"
                  :charge-type "Fixed"
                  :category-id (:beauty category-id)
                  :critical-service false
                  :desc "..."}])