(ns greed.core
  (:require [clojure.core.typed :as t]
            [greed.types :as t2]
            [greed.display :as d]
            [greed.input :as i]
            [greed.grid :as g]
            [greed.player :as p])
  (:gen-class))

(t/ann player-turn
       (t/IFn [t2/Grid t2/Direction t/Bool -> nil]
              [t2/Grid t/Bool -> nil]
              [t2/Grid -> nil]))
(defn player-turn
  "This function is called recursively over and over, serving as the game loop."
  ([grid direction show-moves]
     (player-turn (p/move-player grid direction) show-moves))

  ([grid show-moves]
     (d/print-screen grid show-moves)
     (when-not (= '() (p/get-player-moves grid))
       (condp = (i/wait-for-key)
         \q (do
              (d/print-message "Really quit? (y/n)" grid)
              (when-not (= (i/wait-for-key) \y)
                (d/print-message "                  " grid)
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

(t/ann exit-game [t2/Grid -> nil])
(defn exit-game
  [grid]
  (d/print-message "Game over. Press q to exit." grid) 
  (while (not= \q (i/wait-for-key))))

(t/ann -main [& :optional {:args t/Any} -> nil])
(defn -main
  "Run the game."
  [& args]
  (d/start-display)
  (let [grid (p/place-character-on-grid (g/generate-grid 22 79))]
    (player-turn grid)
    (exit-game grid))
  (d/stop-display))
