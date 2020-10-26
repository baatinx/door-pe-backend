(ns doorpe.backend.home-page
  (:require [ring.util.response :as response]))

(defn home-page
  [req]
  (response/response "this is home page"))