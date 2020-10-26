(ns doorpe.backend.home-page-test
  (:require [clojure.test :refer [deftest testing is]]
            [doorpe.backend.home-page :refer :all]
            [ring.mock.request :as mock]))

(deftest test-home-page
  (testing "test 404"
    (let [response (home-page (mock/request :get "http://localhost:7000"))]
      (is (= (:status response) 200)))))