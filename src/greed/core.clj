(ns greed.core
  (:require [greed.display :refer :all]
            [greed.input :refer :all]
            [greed.grid :refer :all]
            [greed.player :refer :all])
  (:gen-class))

(defn player-turn
  "This function is called recursively over and over, serving as the game loop."
  ([grid direction show-moves]
     (player-turn (move-player grid direction) show-moves))

  ([grid show-moves]
     (print-screen grid show-moves)
     (when-not (= '() (get-player-moves grid))
       (condp = (wait-for-key)
         \q (do
              (print-message "Really quit? (y/n)" grid)
              (when-not (= (wait-for-key) \y)
                (print-message "                  " grid)
                (player-turn grid show-moves)))
         \y (player-turn grid :northwest show-moves)
         \k (player-turn grid :north show-moves)
         \u (player-turn grid :northeast show-moves)
         \l (player-turn grid :east show-moves)
         \n (player-turn grid :southeast show-moves)
         \j (player-turn grid :south show-moves)
         \b (player-turn grid :southwest show-moves)
         \h (player-turn grid :west show-moves)
         \p (player-turn grid (not show-moves))
         (player-turn grid show-moves))))

  ([grid]
     (player-turn grid false)))

(defn exit-game
  [grid]
  (print-message "Game over. Press q to exit." grid) 
  (while (not= \q (wait-for-key))))

(defn -main
  "Run the game."
  [& args]
  (start-display)
  (let [grid (place-character-on-grid (generate-grid 22 79))]
    (player-turn grid)
    (exit-game grid))
  (stop-display))
