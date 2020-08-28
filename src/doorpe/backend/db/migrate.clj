(ns doorpe.backend.db.migrate
  (:require [doorpe.backend.db.ingestion :as insert]
            [doorpe.backend.db.entities.category :as category-entity]
            [doorpe.backend.db.entities.services :as services-entity]))

(defn ^:private migrate-entity-batch
  [coll docs]
  (insert/docs coll docs))

(defn run-migrations
  []
  (migrate-entity-batch category-entity/entity-name category-entity/entity-vec)
  (migrate-entity-batch services-entity/entity-name services-entity/entity-vec))

(run-migrations)
