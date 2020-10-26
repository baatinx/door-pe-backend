(ns doorpe.backend.util-test
  (:require [clojure.test :refer [deftest testing is]]
            [doorpe.backend.util :refer :all]
            [doorpe.backend.db.db :as db]
            [monger.util :refer [object-id]]))

(deftest backend-util-test
  (testing "check whether mongodb collection exists and is not empty"
    (let [db (db/get-db-ref)]
      (is (= (exists-and-not-empty? db "bookings") true))
      (is (= (exists-and-not-empty? db "unknown-coll") false))
      (is (= (exists-and-not-empty? db "") false))
      (is (= (exists-and-not-empty? db 12345) false))
      (is (= (exists-and-not-empty? db true) false))
      (is (= (exists-and-not-empty? db false) false))
      (is (= (exists-and-not-empty? db nil) false))))

  (testing "testing whether a string is valid hexa-string"
    (let [hexa-str (str (object-id))]
      (is (= (valid-hexa-string? hexa-str) true))
      (is (= (valid-hexa-string? "abc") false))
      (is (= (valid-hexa-string? "") false))
      (is (= (valid-hexa-string? 123) false))
      (is (= (valid-hexa-string? true) false))
      (is (= (valid-hexa-string? false) false))
      (is (= (valid-hexa-string? nil) false))))

  (testing "testing bson object id to string conversion"
    (let [oid (object-id)
          s (str oid)]
      (is (= (bson-object-id->str oid) s))
      (is (= (bson-object-id->str 123) false))
      (is (= (bson-object-id->str true) false))
      (is (= (bson-object-id->str false) false))
      (is (= (bson-object-id->str nil) false))
      (is (= (bson-object-id->str "") false))
      (is (= true (instance? String (bson-object-id->str))))))

  (testing "testing wheter a collection exists in db"
    (let [db (db/get-db-ref)]
      (is (= (coll-exists? db "bookings") true))
      (is (= (coll-exists? db "unknown") false))
      (is (= (coll-exists? db 123) false))
      (is (= (coll-exists? db true) false))
      (is (= (coll-exists? db false) false))
      (is (= (coll-exists? db nil) false))))

  (testing "testing wheter a :_id key value in map m gets converted to string instance s"
    (let [oid (object-id)
          s (str oid)]
      (is (= (doc-object-id->str {:_id oid}) {:_id s}))
      (is (= (doc-object-id->str {:_id ""}) false))
      ; (is (= (doc-object-id->str nil) false))
      ; (is (= (doc-object-id->str false) false))
      ; (is (= (doc-object-id->str 123) false))
      (is (= (doc-object-id->str {:_id 1}) false))
      (is (= (doc-object-id->str {:_id true}) false))
      (is (= (doc-object-id->str {:_id true}) false))
      (is (= (doc-object-id->str {:_id false}) false))
      (is (= (doc-object-id->str {:_id nil}) false))))

  (testing "testing wheter a custom key value in map m gets converted to string instance s"
    (let [oid (object-id)
          s (str oid)]
      (is (= (doc-custom-object-id->str {:service-id oid} :service-id) {:service-id s}))
      (is (= (doc-custom-object-id->str {:name "Danish"} :service-id) {:name "Danish"}))
      ; (is (= (doc-custom-object-id->str 123 :service-id) false))
      ; (is (= (doc-custom-object-id->str "hello" :service-id) false))
      ; (is (= (doc-custom-object-id->str true :service-id) false))
      ; (is (= (doc-custom-object-id->str false :service-id) false))
      ; (is (= (doc-custom-object-id->str nil :service-id) false))
      (is (= (doc-custom-object-id->str {:service-id oid} "") false))
      (is (= (doc-custom-object-id->str {:service-id oid} 123) false))
      (is (= (doc-custom-object-id->str {:service-id oid} true) false))
      (is (= (doc-custom-object-id->str {:service-id oid} false) false))
      (is (= (doc-custom-object-id->str {:service-id oid} nil) false))))

  (testing "testing whether a valid mongodb collection name"
    (is (= (valid-coll-name? "abc") true))
    (is (= (valid-coll-name? "nil") true))
    (is (= (valid-coll-name? "") false))
    (is (= (valid-coll-name? 123) false))
    (is (= (valid-coll-name? true) false))
    (is (= (valid-coll-name? false) false))
    (is (= (valid-coll-name? nil) false)))

  (testing "testing whether a valid mongodb collection name"
    (let [oid (object-id)
          s (str oid)]
      (is (= (str->int "123") 123))
      (is (= (str->int "1234567890") 1234567890))
      (is (= (str->int 123) 123))
      (is (= (str->int "abc") false))
      (is (= (str->int "") false))
      (is (= (str->int true) false))
      (is (= (str->int false) false))
      (is (= (str->int nil) false)))))

