(ns greed.grid-test
  (:require [clojure.test :refer :all]
            [greed.test-helper :refer :all]
            [greed.grid :refer :all]))

(deftest test-traverse-grid
  (testing "Could not traverse grid."
    (is (= {:position '(2 0) :moves-made 1}
           (traverse-grid test-grid '(2 1) :north)))
    (is (= {:position '(0 0) :moves-made 0}
           (traverse-grid test-grid '(0 0) :northwest)))
    (is (= {:position '(1 1) :moves-made 1}
           (traverse-grid test-grid '(0 0) :southeast)))
    (is (= {:position '(2 4) :moves-made 3}
           (traverse-grid test-grid '(2 7) :north)))
    (is (= {:position '(6 1) :moves-made 4}
           (traverse-grid test-grid '(2 1) :east)))
    (is (= {:position '(8 6) :moves-made 0}
           (traverse-grid test-grid '(8 6) :southeast)))
    (is (= {:position '(6 4) :moves-made 4}
           (traverse-grid test-grid '(2 0) :southeast)))))

(deftest test-zeroing-paths
  (testing "Could not zero path"
    (let [zeroed-grid (zero-path-between test-grid :east '(2 0) '(6 0))]
      (is (= 3 (get-in zeroed-grid '(0 2))))
      (is (= 0 (get-in zeroed-grid '(0 3))))
      (is (= 0 (get-in zeroed-grid '(0 4))))
      (is (= 0 (get-in zeroed-grid '(0 5))))
      (is (= 7 (get-in zeroed-grid '(0 6)))))))

