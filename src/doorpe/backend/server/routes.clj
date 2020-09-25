(ns doorpe.backend.server.routes
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [muuntaja.middleware :refer [wrap-format]]
            [doorpe.backend.server.handler :as handler]
            [doorpe.backend.server.authentication :refer [auth-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]))

(defroutes app-routes
  (context "/" []
    (GET "/" [] "Door Pe Home page")
    (GET "/send-otp/:contact" [] handler/send-otp)

    (POST "/register-as-customer" [] handler/register-as-customer!)
    (POST "/register-as-service-provider" [] handler/register-as-service-provider!)
    (POST "/login" [] handler/login)
    (POST "/logout" [] handler/logout))

  (context "/customer" []
    (GET "/dashboard" [] handler/customer-dashboard)
    (GET "/show-all" [] handler/customer-show-all))
  (GET "/inspect-request-map" [] handler/inspect-request-map)
  (route/not-found "page not found"))

(def app
  (-> app-routes
      (wrap-authentication auth-backend)
      (wrap-authorization auth-backend)
      (wrap-cors :access-control-allow-origin [#"http://localhost:8000" #"http://localhost:7000" #"http://."]
                 :access-control-allow-methods [:get :put :post :delete])
      wrap-format
      wrap-keyword-params
      wrap-params))