(ns greed.display-test
  (:require [clojure.test :refer :all]
            [greed.test-helper :refer :all]
            [greed.display :refer :all]))

(deftest test-grid-item-display
  (testing "Colors are not correct."
    (is (= {:string " " :color {:fg :white}} (get-grid-item test-grid 2 2)))
    (is (= {:string "@" :color {:fg :white}} (get-grid-item test-grid 2 1)))
    (is (= {:string "1" :color {:fg :yellow}} (get-grid-item test-grid 0 0)))
    (is (= {:string "2" :color {:fg :red}} (get-grid-item test-grid 1 0)))
    (is (= {:string "3" :color {:fg :green}} (get-grid-item test-grid 2 0)))
    (is (= {:string "4" :color {:fg :cyan}} (get-grid-item test-grid 3 0)))
    (is (= {:string "5" :color {:fg :magenta}} (get-grid-item test-grid 4 0)))
    (is (= {:string "6" :color {:fg :yellow}} (get-grid-item test-grid 5 0)))
    (is (= {:string "7" :color {:fg :red}} (get-grid-item test-grid 6 0)))
    (is (= {:string "8" :color {:fg :green}} (get-grid-item test-grid 7 0)))
    (is (= {:string "9" :color {:fg :cyan}} (get-grid-item test-grid 8 0)))))

