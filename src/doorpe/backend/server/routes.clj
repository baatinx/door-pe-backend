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
            [doorpe.backend.all-categories :refer [all-categories]]
            [doorpe.backend.all-services :refer [all-services]]
            [doorpe.backend.all-services-by-category-id :refer [all-services-by-category-id]]
            [doorpe.backend.all-service-providers-by-service-id :refer [all-service-providers-by-service-id]]

            [doorpe.backend.dashboard :refer [dashboard]]
            [doorpe.backend.my-bookings :refer [my-bookings]]
            [doorpe.backend.book-a-service :refer [book-a-service]]
            [doorpe.backend.cancel-booking :refer [cancel-booking]]
            [doorpe.backend.accept-booking :refer [accept-booking]]
            [doorpe.backend.send-otp :refer [send-otp]]
            [doorpe.backend.server.logout :refer [logout]]))

(defroutes app-routes
  (context "/" []
    (GET "/" [] home-page)
    (GET "/send-otp/:contact" [] send-otp)
    (GET "/dashboard" [] dashboard)
    (GET "/my-bookings" [] my-bookings)
    (GET "/all-categories" [] all-categories)
    (GET "/all-services" [] all-services)
    (GET "/all-services-by-category-id/:category-id" [] all-services-by-category-id)
    (GET "/all-service-providers-by-service-id/:service-id" [] all-service-providers-by-service-id)

    (POST "/register" [] register)
    (POST "/login" [] login)
    (POST "/book-a-service" [] book-a-service)
    (POST "/cancel-booking/:booking-id" [] cancel-booking)
    (POST "/accept-booking/:booking-id" [] accept-booking)
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