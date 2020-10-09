(ns doorpe.backend.db.entities.categories
  (:require [monger.util :refer [object-id]]))

(def entity-name "categories")

(def category-id  {:home (object-id)
                   :clean (object-id)
                   :beauty (object-id)})

(def entity-vec [{:_id (:home category-id)
                  :name "Home Appliances/Hardware Repair"
                  :description "Get Experts and background verified professional at home for service like AC Repair, Oven ... "}
                 {:_id (:clean category-id)
                  :name "Cleaning & Disinfection"
                  :description "Description ..."}
                 {:_id (:beauty category-id)
                  :name "Beauty and personal care"
                  :description "Description ..."}])