(ns greed.core
  (:require [greed.coords :refer :all]
            [greed.grid :refer :all]
            [greed.player :refer :all]
            [lanterna.screen :as s])
  (:gen-class))

(def scr (s/get-screen))

(defn print-grid
  "Prints the grid to the screen."
  [grid]
  (doseq [x (range (count (first grid))) y (range (count grid))]
    (s/put-string scr x y
                  (let [thing (get-in grid [y x])]
                    (if (= thing 0)
                      " "
                      (print-str thing)))))
 (let [player-position (find-player grid)]
   (s/move-cursor scr (first player-position) (second player-position)))
 (s/redraw scr)
 grid)

(defn print-message
  "Prints a message below the grid."
  [message grid]
  (s/put-string scr 0 (inc (count grid)) message)
  (s/redraw scr))

(defn player-turn
  "This function is called recursively over and over, serving as the game loop."
  [grid]
  (print-grid grid)
  (if (= '() (get-player-moves grid))
    (print-message "Game over! Press q to exit." grid)
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
        (player-turn grid)))))

(defn generate-grid
  "Generates a random grid of size HEIGHT x WIDTH, and randomly places player."
  [height width]
  (assoc-in 
   (into []
         (repeatedly height (fn [] 
                              (into []
                                    (repeatedly width (fn []
                                                        (rand-grid-item)))))))
   (list (rand-int height) (rand-int width))
   player-character))

(defn -main
  "Run the game."
  [& args]
  (s/start scr)
  (player-turn (generate-grid 22 79))
  (while (not= \q (s/get-key-blocking scr)))
  (s/stop scr))
