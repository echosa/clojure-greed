(ns greed.core
  (:require [greed.coords :refer :all]
            [greed.grid :refer :all]
            [greed.player :refer :all])
  (:gen-class))

(defn print-grid [grid]
  "Prints the grid to the screen."
  (mapcat (fn [x]
            (mapcat (fn [y]
                      (print (if (= y 0) " " y) " "))
                    x)
            (print "\n"))
          grid)
  (print "\n"))

(defn -main
  "Run the game."
  [& args]
  (let [grid [[1 2 3 4 5]
              [1 2 3 4 5]
              [1 2 "@" 4 5]
              [1 2 3 4 5]
              [1 2 3 4 5]]]
    (print-grid grid)
    (print-grid (move-player grid :west))
    (print-grid (move-player (move-player grid :west) :north))
    ))
