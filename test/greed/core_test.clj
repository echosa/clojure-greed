(ns greed.core-test
  (:require [clojure.test :refer :all]
            [greed.core :refer :all]))

(def test-grid '((1 2  3  4 5 6 7 8 9)
                 (2 2 "@" 4 5 6 7 8 9)
                 (3 2  0  4 5 6 7 8 9)
                 (4 2  3  4 5 6 7 8 9)
                 (5 2  3  4 5 6 7 8 9)
                 (6 2  3  4 5 6 0 8 9)
                 (7 2  3  4 5 6 7 8 9)
                 (8 2  3  4 5 6 7 8 9)))

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

(deftest test-finding-player-in-grid
  (testing "Could not find player in the game grid."
    (is (= '(2 1) (find-player test-grid)))))

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

(deftest test-finding-available-moves
  (testing "Could not find available moves."
    (let [moves (get-player-moves test-grid)]
      (is (= -1 (.indexOf moves :northwest)))
      (is (= -1 (.indexOf moves :north)))
      (is (= -1 (.indexOf moves :northeast)))
      (is (< -1 (.indexOf moves :east)))
      (is (= -1 (.indexOf moves :southeast)))
      (is (= -1 (.indexOf moves :south)))
      (is (< -1 (.indexOf moves :southwest)))
      (is (< -1 (.indexOf moves :west))))))
