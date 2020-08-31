(ns doorpe.backend.db.query
  (:require [monger.collection :as mc]
            [doorpe.backend.db.db :refer [get-db-ref]]
            [doorpe.backend.util :refer [exists?]]))

(def ^:private db (get-db-ref ))

(defn customers
  []
  (let [coll "customers"]
    (if (exists? db coll)
      (mc/find-maps db coll)
      nil)))
