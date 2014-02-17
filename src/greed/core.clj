(ns greed.core
  (:require [greed.display :refer :all]
            [greed.grid :refer :all]
            [greed.player :refer :all]
            [lanterna.screen :as s])
  (:gen-class))

(defn player-turn
  "This function is called recursively over and over, serving as the game loop."
  [grid scr]
  (print-grid grid scr)
  (if (= '() (get-player-moves grid))
    (print-message "Game over! Press q to exit." grid scr)
    (let [key (s/get-key-blocking scr)]
      (condp = key
        \q nil
        \y (player-turn (move-player grid :northwest) scr)
        \k (player-turn (move-player grid :north) scr)
        \u (player-turn (move-player grid :northeast) scr)
        \l (player-turn (move-player grid :east) scr)
        \n (player-turn (move-player grid :southeast) scr)
        \j (player-turn (move-player grid :south) scr)
        \b (player-turn (move-player grid :southwest) scr)
        \h (player-turn (move-player grid :west) scr)
        (player-turn grid scr)))))

(defn -main
  "Run the game."
  [& args]
  (let [scr (s/get-screen)
        grid (place-character-on-grid (generate-grid 22 79))]
    (s/start scr)
    (player-turn grid scr)
    (while (not= \q (s/get-key-blocking scr)))
    (s/stop scr)))
