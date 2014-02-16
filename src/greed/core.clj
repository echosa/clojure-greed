(ns greed.core
  (:require [greed.coords :refer :all]
            [greed.grid :refer :all]
            [greed.player :refer :all]
            [lanterna.screen :as s])
  (:gen-class))

(def scr (s/get-screen))

(defn print-grid [grid]
  "Prints the grid to the screen."
  (doseq [x (range (count (first grid))) y (range (count grid))]
    (s/put-string scr x (inc (inc y))
                  (let [thing (get-in grid [y x])]
                    (if (= thing 0)
                      " "
                      (print-str thing)))))
 (s/redraw scr))

(defn -main
  "Run the game."
  [& args]
  (let [grid [[1 2 3 4 5]
              [1 2 3 4 5]
              [1 2 "@" 4 5]
              [1 2 3 4 5]
              [1 2 3 4 5]]]
    (s/start scr)

    (print-grid grid)
    (s/get-key-blocking scr)
    (print-grid (move-player grid :west))
    (s/get-key-blocking scr)
    (print-grid (move-player (move-player grid :west) :north))
    (s/get-key-blocking scr)
    (print-grid (move-player (move-player (move-player grid :west) :north) :north))
    (s/get-key-blocking scr)
    (print-grid (move-player (move-player (move-player (move-player grid :west) :north) :north) :east))


    (s/put-string scr 10 11 "Press any key to exit!")
    (s/redraw scr)
    (s/get-key-blocking scr)

    (s/stop scr)))
