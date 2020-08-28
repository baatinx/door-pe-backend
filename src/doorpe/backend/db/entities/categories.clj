(ns doorpe.backend.db.entities.categories
  (:import [org.bson.types ObjectId]))

(def entity-name "categories")

(def entity-vec [{:_id (ObjectId.)
                  :category-name "Home Appliances/Hardware Repair"
                  :desc "Get Experts and background verified professional at home for service like AC Repair, Oven ... "}
                 {:_id (ObjectId.)
                  :category-name "Cleaning & Disinfection"
                  :desc "Description ..."}
                 {:_id (ObjectId.)
                  :category-name "Beauty and personal care"
                  :desc "Description ..."}
                 {:_id (ObjectId.)
                  :category-name "Computer and Gatgets Repair"
                  :desc "Description ..."}
                 {:_id (ObjectId.)
                  :category-name "Renovation Services"
                  :desc "Description ..."}
                 {:_id (ObjectId.)
                  :category-name "Health and Fitness"
                  :desc "Description ..."}
                 {:_id (ObjectId.)
                  :category-name "Tutors"
                  :desc "Description ..."}
                 {:_id (ObjectId.)
                  :category-name "laundry services"
                  :desc "Description"}])
