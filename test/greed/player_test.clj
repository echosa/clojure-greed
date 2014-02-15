(ns greed.player-test
  (:require [clojure.test :refer :all]
            [greed.test-helper :refer :all]
            [greed.player :refer :all]))

(deftest test-finding-player-in-grid
  (testing "Could not find player in the game grid."
    (is (= '(2 1) (find-player test-grid)))))

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
