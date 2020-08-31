(ns doorpe.backend.db.migrate
  (:require [doorpe.backend.db.ingestion :as insert]
            [doorpe.backend.db.entities.categories :as categories]
            [doorpe.backend.db.entities.services :as services]
            [doorpe.backend.db.entities.customers :as customers]
            [doorpe.backend.db.entities.providers :as providers]
            [doorpe.backend.db.entities.bookings :as bookings]
            [doorpe.backend.db.entities.reviews :as reviews]
            [doorpe.backend.db.entities.complaints :as complaints]
            [doorpe.backend.db.entities.authentication :as authentication]))

(defn ^:private migrate-entity-batch
  [coll docs]
  (insert/docs coll docs))

(defn run-migrations
  []
  (migrate-entity-batch categories/entity-name categories/entity-vec)
  (migrate-entity-batch services/entity-name services/entity-vec)
  (migrate-entity-batch customers/entity-name customers/entity-vec)
  (migrate-entity-batch providers/entity-name providers/entity-vec)
  (migrate-entity-batch bookings/entity-name bookings/entity-vec)
  (migrate-entity-batch reviews/entity-name reviews/entity-vec)
  (migrate-entity-batch complaints/entity-name complaints/entity-vec)
  (migrate-entity-batch authentication/entity-name authentication/entity-vec)
  "Migrations Successfull!")