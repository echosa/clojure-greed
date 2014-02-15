(ns greed.player-test
  (:require [clojure.test :refer :all]
            [greed.test-helper :refer :all]
            [greed.player :refer :all]
            [greed.grid :refer :all]))

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

(deftest test-moving-player-makes-stating-position-a-zero
  (testing "Moving the player didn't make the starting space a zero."
    (is (= 0 (get-thing-at-grid-position
              (move-player test-grid :east)
              '(2 1))))))

(deftest test-moving-player-makes-end-point-the-player-character
  (testing "Moving the player didn't make the end point the player character"
    (is (= player-character (get-thing-at-grid-position
                             (move-player test-grid :east)
                             '(6 1))))))

(deftest test-moving-player-makes-all-traversed-points-zeroes
  (testing "Moving the player didn't make the traversed points zeroes."
    (let [grid-after-move (move-player test-grid :east)]
      (is (= 0 (get-thing-at-grid-position grid-after-move '(3 1))))
      (is (= 0 (get-thing-at-grid-position grid-after-move '(4 1))))
      (is (= 0 (get-thing-at-grid-position grid-after-move '(5 1)))))))

(deftest test-making-bad-move-does-not-modify-grid
  (testing "Trying to make a bad move modified the grid."
    (is (= test-grid (move-player test-grid :south)))))
