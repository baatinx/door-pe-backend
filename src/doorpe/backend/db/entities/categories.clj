(ns doorpe.backend.db.entities.categories
  (:require [monger.util :refer [object-id]]))

(def entity-name "categories")

(def category-id  {:home (object-id)
                   :clean (object-id)
                   :beauty (object-id)})

(def entity-vec [{:_id (:home category-id)
                  :category-name "Home Appliances/Hardware Repair"
                  :desc "Get Experts and background verified professional at home for service like AC Repair, Oven ... "}
                 {:_id (:clean category-id)
                  :category-name "Cleaning & Disinfection"
                  :desc "Description ..."}
                 {:_id (:beauty category-id)
                  :category-name "Beauty and personal care"
                  :desc "Description ..."}])