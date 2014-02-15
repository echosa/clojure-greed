(ns greed.player
  (:require [greed.grid :refer :all]
            [greed.coords :refer :all]))

(def player-character 
  "The character that represents the player."
  "@")

(defn find-player [grid]
  "Returns the (X Y) position of the player."
  (let [row (first (filter #(> (.indexOf % player-character) -1)  grid))]
    (list (.indexOf row player-character) (.indexOf grid row))))

(defn player-move-in-direction [grid player-position direction]
  "Returns the direction if the player can move that way, nil otherwise."
  (let [move-amount (get-thing-at-grid-position
                     grid (get-next-coordinate grid player-position direction))
        moves-made ((traverse-grid-in-direction grid player-position direction)
                    :moves-made)]
    (when (and (not (= move-amount 0))
               (= move-amount moves-made))
      direction)))

(defn get-player-moves [grid]
  "Returns keywords for the directions in which a player can move."
  (let [player-position (find-player grid)
        moves (list)]
    (list
     (player-move-in-direction grid player-position :northwest)
     (player-move-in-direction grid player-position :north)
     (player-move-in-direction grid player-position :northeast)
     (player-move-in-direction grid player-position :west)
     (player-move-in-direction grid player-position :southwest)
     (player-move-in-direction grid player-position :south)
     (player-move-in-direction grid player-position :southeast)
     (player-move-in-direction grid player-position :east))))
