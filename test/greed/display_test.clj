(ns greed.display-test
  (:require [clojure.test :refer :all]
            [greed.test-helper :refer :all]
            [greed.display :refer :all]))

(deftest test-grid-item-display
  (testing "Colors are not correct."
    (is (= {:string " " :color {:fg :white :bg :default}}
           (get-grid-item test-grid [2 2] false)))
    (is (= {:string "@" :color {:fg :white}}
           (get-grid-item test-grid [2 1] false)))
    (is (= {:string "1" :color {:fg :yellow :bg :default}}
           (get-grid-item test-grid [0 0] false)))
    (is (= {:string "2" :color {:fg :red :bg :default}}
           (get-grid-item test-grid [1 0] false)))
    (is (= {:string "3" :color {:fg :green :bg :default}}
           (get-grid-item test-grid [2 0] false)))
    (is (= {:string "4" :color {:fg :cyan :bg :default}}
           (get-grid-item test-grid [3 0] false)))
    (is (= {:string "5" :color {:fg :magenta :bg :default}}
           (get-grid-item test-grid [4 0] false)))
    (is (= {:string "6" :color {:fg :yellow :bg :default}}
           (get-grid-item test-grid [5 0] false)))
    (is (= {:string "7" :color {:fg :red :bg :default}}
           (get-grid-item test-grid [6 0] false)))
    (is (= {:string "8" :color {:fg :green :bg :default}}
           (get-grid-item test-grid [7 0] false)))
    (is (= {:string "9" :color {:fg :cyan :bg :default}}
           (get-grid-item test-grid [8 0] false)))))

