(ns doorpe.backend.server.routes
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.util.response :as response]
            [compojure.middleware :as middleware]))

 (defroutes app-routes
   (GET "/" req {:status 200
                 :headers {"Content-Type" "text/html"}
                 :body "Hello World"})
   (GET "/admin/users" req (response/response "users"))
   (route/not-found "Not found"))

 (def app app-routes)