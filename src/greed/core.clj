(ns greed.core
  (:require [greed.display :refer :all]
            [greed.input :refer :all]
            [greed.grid :refer :all]
            [greed.player :refer :all])
  (:gen-class))

(defn player-turn
  "This function is called recursively over and over, serving as the game loop."
  ([grid show-moves]
     (print-screen grid show-moves)
     (when-not (= '() (get-player-moves grid))
       (let [key (wait-for-key)]
         (condp = key
           \q (do
                (print-message "Really quit? (y/n)" grid)
                (when-not (= (wait-for-key) \y)
                  (print-message "                  " grid)
                  (player-turn grid show-moves)))
           \y (player-turn (move-player grid :northwest) show-moves)
           \k (player-turn (move-player grid :north) show-moves)
           \u (player-turn (move-player grid :northeast) show-moves)
           \l (player-turn (move-player grid :east) show-moves)
           \n (player-turn (move-player grid :southeast) show-moves)
           \j (player-turn (move-player grid :south) show-moves)
           \b (player-turn (move-player grid :southwest) show-moves)
           \h (player-turn (move-player grid :west) show-moves)
           \p (player-turn grid (not show-moves))
           (player-turn grid show-moves)))))

  ([grid]
     (player-turn grid false)))

(defn -main
  "Run the game."
  [& args]
  (start-display)
  (let [grid (place-character-on-grid (generate-grid 22 79))]
    (player-turn grid)
    (print-message "Game over. Press q to exit." grid))
  (while (not= \q (wait-for-key)))
  (stop-display))
