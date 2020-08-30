(ns doorpe.backend.db.entities.categories
  (:require [doorpe.backend.util :refer [bson-object-id]]))

(def entity-name "categories")

(def entity-vec [{:_id (bson-object-id)
                  :category-name "Home Appliances/Hardware Repair"
                  :desc "Get Experts and background verified professional at home for service like AC Repair, Oven ... "}
                 {:_id (bson-object-id)
                  :category-name "Cleaning & Disinfection"
                  :desc "Description ..."}
                 {:_id (bson-object-id)
                  :category-name "Beauty and personal care"
                  :desc "Description ..."}
                 {:_id (bson-object-id)
                  :category-name "Computer and Gatgets Repair"
                  :desc "Description ..."}
                 {:_id (bson-object-id)
                  :category-name "Renovation Services"
                  :desc "Description ..."}
                 {:_id (bson-object-id)
                  :category-name "Health and Fitness"
                  :desc "Description ..."}
                 {:_id (bson-object-id)
                  :category-name "Tutors"
                  :desc "Description ..."}
                 {:_id (bson-object-id)
                  :category-name "laundry services"
                  :desc "Description"}])
