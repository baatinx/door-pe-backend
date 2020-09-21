(ns doorpe.backend.server.routes
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [muuntaja.middleware :refer [wrap-format]]
            [doorpe.backend.server.handler :as handler]))

(defroutes app-routes
  (context "/" []
    (GET "/" [] "Hello World")
    (GET "/customers" [] handler/customers)
    (GET "/customer/:id" [] handler/customer)
    (POST "/register-as-customer" [] handler/register-as-customer!)
    (POST "/register-as-service-provider" [] handler/register-as-service-provider!)
    (GET "/send-otp/:contact" [] handler/send-otp))


  (GET "/inspect/:id" [] handler/inspect)
  (route/not-found "page not found"))

(def app
  (-> app-routes
      (wrap-cors :access-control-allow-origin [#"http://localhost:8000"]
                 :access-control-allow-methods [:get :put :post :delete])
      wrap-format
      wrap-keyword-params
      wrap-params))