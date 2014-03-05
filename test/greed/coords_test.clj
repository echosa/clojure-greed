(ns greed.coords-test
  (:require [clojure.test :refer :all]
            [greed.coords :refer :all]
            [greed.test-helper :refer :all]))

(deftest test-getting-next-northwest-coord
  (testing "Could not get the next coordinate to the northwest."
    (is (= nil (get-next-coordinate-northwest test-grid [0 0])))
    (is (= nil (get-next-coordinate-northwest test-grid [0 1])))
    (is (= nil (get-next-coordinate-northwest test-grid [1 0])))
    (is (= '(8 8) (get-next-coordinate-northwest test-grid [9 9])))
    (is (= '(0 0) (get-next-coordinate-northwest test-grid [1 1])))))

(deftest test-getting-next-north-coord
  (testing "Could not get the next coordinate to the north."
    (is (= nil (get-next-coordinate-north test-grid [0 0])))
    (is (= '(0 0) (get-next-coordinate-north test-grid [0 1])))
    (is (= nil (get-next-coordinate-north test-grid [1 0])))
    (is (= '(9 8) (get-next-coordinate-north test-grid [9 9])))
    (is (= '(1 0) (get-next-coordinate-north test-grid [1 1])))))

(deftest test-getting-next-northeast-coord
  (testing "Could not get the next coordinate to the northeast."
    (is (= nil (get-next-coordinate-northeast test-grid [0 0])))
    (is (= '(1 0) (get-next-coordinate-northeast test-grid [0 1])))
    (is (= nil (get-next-coordinate-northeast test-grid [1 0])))
    (is (= nil (get-next-coordinate-northeast test-grid [9 9])))
    (is (= '(2 0) (get-next-coordinate-northeast test-grid [1 1])))))

(deftest test-getting-next-east-coord
  (testing "Could not get the next coordinate to the east."
    (is (= '(1 0) (get-next-coordinate-east test-grid [0 0])))
    (is (= '(1 1) (get-next-coordinate-east test-grid [0 1])))
    (is (= '(2 0) (get-next-coordinate-east test-grid [1 0])))
    (is (= nil (get-next-coordinate-east test-grid [9 9])))
    (is (= '(2 1) (get-next-coordinate-east test-grid [1 1])))))

(deftest test-getting-next-southeast-coord
  (testing "Could not get the next coordinate to the southeast."
    (is (= '(1 1) (get-next-coordinate-southeast test-grid [0 0])))
    (is (= '(1 2) (get-next-coordinate-southeast test-grid [0 1])))
    (is (= '(2 1) (get-next-coordinate-southeast test-grid [1 0])))
    (is (= nil (get-next-coordinate-southeast test-grid [9 9])))
    (is (= nil (get-next-coordinate-southeast test-grid [8 6])))
    (is (= '(2 2) (get-next-coordinate-southeast test-grid [1 1])))))

(deftest test-getting-next-south-coord
  (testing "Could not get the next coordinate to the south."
    (is (= '(0 1) (get-next-coordinate-south test-grid [0 0])))
    (is (= '(0 2) (get-next-coordinate-south test-grid [0 1])))
    (is (= '(1 1) (get-next-coordinate-south test-grid [1 0])))
    (is (= nil (get-next-coordinate-south test-grid [9 9])))
    (is (= '(1 2) (get-next-coordinate-south test-grid [1 1])))))

(deftest test-getting-next-southwest-coord
  (testing "Could not get the next coordinate to the southwest."
    (is (= nil (get-next-coordinate-southwest test-grid [0 0])))
    (is (= nil (get-next-coordinate-southwest test-grid [0 1])))
    (is (= '(0 1) (get-next-coordinate-southwest test-grid [1 0])))
    (is (= nil (get-next-coordinate-southwest test-grid [9 9])))
    (is (= '(0 2) (get-next-coordinate-southwest test-grid [1 1])))))

(deftest test-getting-next-west-coord
  (testing "Could not get the next coordinate to the west."
    (is (= nil (get-next-coordinate-west test-grid [0 0])))
    (is (= nil (get-next-coordinate-west test-grid [0 1])))
    (is (= '(0 0) (get-next-coordinate-west test-grid [1 0])))
    (is (= '(8 9) (get-next-coordinate-west test-grid [9 9])))
    (is (= '(0 1) (get-next-coordinate-west test-grid [1 1])))))

(defn test-coords []
  (test-getting-next-northwest-coord)
  (test-getting-next-north-coord)
  (test-getting-next-northeast-coord)
  (test-getting-next-east-coord)
  (test-getting-next-southeast-coord)
  (test-getting-next-south-coord)
  (test-getting-next-southwest-coord)
  (test-getting-next-west-coord))

(run-tests)
