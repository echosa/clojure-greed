(ns greed.grid-test
  (:require [clojure.test :refer :all]
            [greed.test-helper :refer :all]
            [greed.grid :refer :all]))

(deftest test-getting-thing-at-grid-position
  (testing "Could not get thing at grid position."
    (is (= nil (get-thing-at-grid-position test-grid nil)))
    (is (= 1 (get-thing-at-grid-position test-grid '(0 0))))
    (is (= "@" (get-thing-at-grid-position test-grid '(2 1))))
    (is (= 8 (get-thing-at-grid-position test-grid '(0 7))))
    (is (= 9 (get-thing-at-grid-position test-grid '(8 0))))
    (is (= 9 (get-thing-at-grid-position test-grid '(8 7))))
    (is (= nil (get-thing-at-grid-position test-grid '(-1 0))))
    (is (= nil (get-thing-at-grid-position test-grid '(0 -1))))
    (is (= nil (get-thing-at-grid-position test-grid '(9 0))))
    (is (= nil (get-thing-at-grid-position test-grid '(0 8))))))

(deftest test-setting-thing-at-grid-position
  (testing "Could not set thing at grid position."
    (let [thing "Z"
          position '(5 5)]
      (is (= thing (get-thing-at-grid-position
                    (set-thing-at-grid-position test-grid position thing)
                    position))))))

(deftest test-traverse-grid
  (testing "Could not traverse grid."
    (is (= {:position '(2 0) :moves-made 1}
           (traverse-grid-in-direction test-grid '(2 1) :north)))
    (is (= {:position '(0 0) :moves-made 0}
           (traverse-grid-in-direction test-grid '(0 0) :northwest)))
    (is (= {:position '(1 1) :moves-made 1}
           (traverse-grid-in-direction test-grid '(0 0) :southeast)))
    (is (= {:position '(2 4) :moves-made 3}
           (traverse-grid-in-direction test-grid '(2 7) :north)))
    (is (= {:position '(6 1) :moves-made 4}
           (traverse-grid-in-direction test-grid '(2 1) :east)))
    (is (= {:position '(8 6) :moves-made 0}
           (traverse-grid-in-direction test-grid '(8 6) :southeast)))
    (is (= {:position '(6 4) :moves-made 4}
           (traverse-grid-in-direction test-grid '(2 0) :southeast)))))

(deftest test-zeroing-paths
  (testing "Could not zero path"
    (let [zeroed-grid (zero-path-between test-grid :east '(2 0) '(6 0))]
      (is (= 3 (get-thing-at-grid-position zeroed-grid '(2 0))))
      (is (= 0 (get-thing-at-grid-position zeroed-grid '(3 0))))
      (is (= 0 (get-thing-at-grid-position zeroed-grid '(4 0))))
      (is (= 0 (get-thing-at-grid-position zeroed-grid '(5 0))))
      (is (= 7 (get-thing-at-grid-position zeroed-grid '(6 0)))))))
