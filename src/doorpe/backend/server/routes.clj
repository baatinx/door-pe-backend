(ns doorpe.backend.server.routes
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [muuntaja.middleware :refer [wrap-format]]
            [doorpe.backend.server.authentication :refer [auth-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [doorpe.backend.home-page :refer [home-page]]
            [doorpe.backend.register :refer [register]]
            [doorpe.backend.server.login :refer [login]]

            [doorpe.backend.dashboard :refer [dashboard]]
            [doorpe.backend.my-bookings :refer [my-bookings]]
            [doorpe.backend.send-otp :refer [send-otp]]
            [doorpe.backend.server.logout :refer [logout]]))

(defroutes app-routes
  (context "/" []
    (GET "/" [] home-page)
    (GET "/send-otp/:contact" [] send-otp)
    (GET "/dashboard" [] dashboard)
    (GET "/my-bookings" [] my-bookings)

    (POST "/register" [] register)
    (POST "/login" [] login)
    (POST "/logout" [] logout))
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