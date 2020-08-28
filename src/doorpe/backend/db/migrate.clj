(ns doorpe.backend.db.migrate
  (:require [doorpe.backend.db.ingestion :as insert]
            [doorpe.backend.db.entities.categories :as categories]
            [doorpe.backend.db.entities.services :as services]
            [doorpe.backend.db.entities.customers :as customers]
            [doorpe.backend.db.entities.bookings :as bookings]))

(defn ^:private migrate-entity-batch
  [coll docs]
  (insert/docs coll docs))

(defn run-migrations
  []
  (migrate-entity-batch categories/entity-name categories/entity-vec)
  (migrate-entity-batch services/entity-name services/entity-vec)
  (migrate-entity-batch customers/entity-name customers/entity-vec)
  (migrate-entity-batch bookings/entity-name bookings/entity-vec))

(run-migrations)
