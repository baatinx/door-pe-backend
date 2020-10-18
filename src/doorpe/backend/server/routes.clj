(ns doorpe.backend.server.routes
  (:require [compojure.core :refer [defroutes context GET POST PUT PATCH DELETE]]
            [compojure.route :as route]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
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
            [doorpe.backend.book-service :refer [book-service]]
            [doorpe.backend.provide-service :refer [provide-service]]
            [doorpe.backend.accept-booking :refer [accept-booking]]
            [doorpe.backend.cancel-booking :refer [cancel-booking]]
            [doorpe.backend.reject-booking :refer [reject-booking]]
            [doorpe.backend.my-profile :refer [my-profile]]
            [doorpe.backend.update-my-profile :refer [update-my-profile]]
            [doorpe.backend.server.send-otp :refer [send-otp]]
            [doorpe.backend.book-complaint :refer [book-complaint]]
            [doorpe.backend.server.logout :refer [logout]]

            [doorpe.backend.admin-add :refer [admin-add]]
            [doorpe.backend.admin-edit :refer [admin-edit]]
            [doorpe.backend.all-service-requests :refer [all-service-requests]]
            [doorpe.backend.admin-service-requests :refer [admin-service-requests]]))

(defroutes app-routes
  (context "/" []
    (GET "/" [] home-page)
    (GET "/send-otp/:contact/:otp-method" [] send-otp)
    (GET "/dashboard" [] dashboard)
    (GET "/my-bookings" [] my-bookings)
    (GET "/all-categories" [] all-categories)
    (GET "/all-services" [] all-services)
    (GET "/all-services-by-category-id/:category-id" [] all-services-by-category-id)
    (GET "/all-service-providers-by-service-id/:service-id" [] all-service-providers-by-service-id)
    (GET "/my-profile" [] my-profile)

    (POST "/register" [] register)
    (POST "/login" [] login)
    (POST "/book-service" [] book-service)
    (POST "/provide-service" [] provide-service)
    (POST "/cancel-booking/:booking-id" [] cancel-booking)
    (POST "/accept-booking/:booking-id" [] accept-booking)
    (POST "/reject-booking/:booking-id" [] reject-booking)
    (POST "/logout" [] logout)
    (POST "/book-complaint" [] book-complaint)

    (POST "/update-my-profile" [] update-my-profile)

    (POST "/admin-add/:add-what" [] admin-add)
    (POST "/admin-edit/:edit-what" [] admin-edit)
    (GET "/all-service-requests" [] all-service-requests)
    (GET "/admin-service-requests" [] admin-service-requests))
  (route/not-found "page not found"))

(def app
  (-> app-routes
      (wrap-authentication auth-backend)
      (wrap-authorization auth-backend)
      (wrap-cors :access-control-allow-origin [#"http://localhost:8000" #"http://localhost:7000" #"http://."]
                 :access-control-allow-methods [:get :put :post :delete])
      wrap-format
      wrap-keyword-params
      wrap-params
      wrap-multipart-params))