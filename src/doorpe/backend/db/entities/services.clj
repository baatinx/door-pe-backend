(ns doorpe.backend.db.entities.services
  (:import [org.bson.types ObjectId]))

(def entity-name "services")

(def entity-vec [{:_id (ObjectId.)
                    :name "Washing Machine Repair"
                    :charge-type "variable"
                    :category "Home Appliances/Hardware Repair"
                    :desc "Problem with your Washing Machine? Not a big deal now, get expert within no time ... we fix all types of Washing maching(Fully-automatic, semi-automatic) from top brands like svmsung, LG, Godrej, whirlpool"}
                   {:_id (ObjectId.)
                    :name "Electrician"
                    :charge-type "variable"
                    :category "..."
                    :desc "..."}
                   {:_id (ObjectId.)
                    :name "Hair Cutting for Mens"
                    :charge-type "Fixed"
                    :category "Beauty and Personal Care"
                    :desc "..."}
                   {:_id (ObjectId.)
                    :name "Hair Cut for Girls"
                    :charge-type "Fixed"
                    :category "Beauty and Personal Care"
                    :desc "..."}
                   {:_id (ObjectId.)
                    :name "Hair Coloring for Girls"
                    :charge-type "Fixed"
                    :category "Beauty and Personal Care"
                    :desc "..."}
                   {:_id (ObjectId.)
                    :name "Water Geyser Repair"
                    :charge-type "variable"
                    :category ""
                    :desc "..."}
                   {:_id (ObjectId.)
                    :name "Water Geyser Cleaning"
                    :charge-type "Fixed"
                    :category "Cleaning & Disinfection"
                    :desc "..."}
                   {:_id (ObjectId.)
                    :name "Water Purifier Repair"
                    :charge-type "variable"
                    :category ""
                    :desc "..."}])