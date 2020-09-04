(ns doorpe.backend.server.routes
  (:require [compojure.core :refer [defroutes GET context]]
            [compojure.route :as route]
            [compojure.middleware :as c-middleware]
            [ring.util.response :as response]
            [muuntaja.middleware :as m-middleware]
            [muuntaja.core :as m]
            [jsonista.core :as j]
            [doorpe.backend.db.query :as query]))
(defroutes app-routes
  (context "/" []
    (GET "/" [] "Hello World")
    (GET "/customers" [] (merge (response/response (query/customers))))
    (GET "/customer/:id" [id] (merge (response/response (query/customer id))))
    (route/not-found "Not found")))

(def app
  (-> app-routes
      m-middleware/wrap-format))