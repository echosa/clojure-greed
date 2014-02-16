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
 (s/redraw scr)
 grid)

(defn player-turn [grid]
  (print-grid grid)
  (let [key (s/get-key-blocking scr)]
    (condp = key
      \q nil
      \y (player-turn (move-player grid :northwest))
      \k (player-turn (move-player grid :north))
      \u (player-turn (move-player grid :northeast))
      \l (player-turn (move-player grid :east))
      \n (player-turn (move-player grid :southeast))
      \j (player-turn (move-player grid :south))
      \b (player-turn (move-player grid :southwest))
      \h (player-turn (move-player grid :west))
      (player-turn grid))))

(defn -main
  "Run the game."
  [& args]
  (let [grid [[1 2 3 4 5]
              [1 2 3 4 5]
              [1 2 "@" 4 5]
              [1 2 3 4 5]
              [1 2 3 4 5]]]
    (s/start scr)
    (player-turn grid)
    (s/stop scr)))
