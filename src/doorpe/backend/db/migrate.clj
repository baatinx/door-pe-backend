(ns doorpe.backend.db.migrate
  (:require [doorpe.backend.db.ingestion :as insert]
            [doorpe.backend.db.entities.category :as category-entity]))

(defn ^:private migrate-category
  []
  (insert/category-batch category-entity/categories-vec))

(defn run-migrations
  []
  (migrate-category))

(run-migrations)
