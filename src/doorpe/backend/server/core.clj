(ns doorpe.backend.server.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [doorpe.backend.server.routes :refer [app]]
           ;; [doorpe.backend.db.migrate :refer [run-migrations]]
            )
  (:gen-class))

(defn run
  [routes]
  (run-jetty routes {:port 7000
                     :join? false}))

(defn -main
  [& args]
  (run app)
  ;;(run-migrations)
  (println "Server Running...")
  )
