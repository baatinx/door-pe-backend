(ns doorpe.backend.server.routes
  (:require [compojure.core :refer [defroutes GET context]]
            [compojure.route :as route]
            [ring.util.response :as response]
            [muuntaja.middleware :as middleware]
            [muuntaja.core :as m]
            [jsonista.core :as j]
            [doorpe.backend.db.query :as query]))

(defn customers
  [req]
  (merge (response/response (query/customers))))

(defn customer
  [id req]
  (merge (response/response (query/customer id))))

(defn inspect
  [id req]
  (str "id <-" id "req <-" (j/write-value-as-string req)))

(defroutes app-routes
  (context "/" []
    (GET "/" [] "Hello World")
    (GET "/customers" [] customers)
    (GET "/customer/:id" [id] (partial customer id))
    (GET "/inspect/:id" [id] (partial inspect id))
    (route/not-found "Not found")))

(def app
  (-> app-routes
      middleware/wrap-format
      middleware/wrap-params))