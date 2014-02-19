(ns greed.core
  (:require [greed.display :refer :all]
            [greed.grid :refer :all]
            [greed.player :refer :all]
            [lanterna.screen :as s])
  (:gen-class))

(defn player-turn
  "This function is called recursively over and over, serving as the game loop."
  [grid show-moves scr]
  (print-screen grid show-moves scr)
  (when-not (= '() (get-player-moves grid))
    (let [key (s/get-key-blocking scr)]
      (condp = key
        \q (do
             (print-message "Really quit? (y/n)" grid scr)
             (when-not (= (s/get-key-blocking scr) \y)
               (print-message "                  " grid scr)
               (player-turn grid show-moves scr)))
        \y (player-turn (move-player grid :northwest) show-moves scr)
        \k (player-turn (move-player grid :north) show-moves scr)
        \u (player-turn (move-player grid :northeast) show-moves scr)
        \l (player-turn (move-player grid :east) show-moves scr)
        \n (player-turn (move-player grid :southeast) show-moves scr)
        \j (player-turn (move-player grid :south) show-moves scr)
        \b (player-turn (move-player grid :southwest) show-moves scr)
        \h (player-turn (move-player grid :west) show-moves scr)
        \p (player-turn grid (not show-moves) scr)
        (player-turn grid show-moves scr)))))

(defn -main
  "Run the game."
  [& args]
  (let [scr (s/get-screen)
        grid (place-character-on-grid (generate-grid 22 79))]
    (s/start scr)
    (player-turn grid false scr)
    (print-message "Game over. Press q to exit." grid scr)
    (while (not= \q (s/get-key-blocking scr)))
    (s/stop scr)))
