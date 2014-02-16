(ns greed.player
  (:require [greed.grid :refer :all]
            [greed.coords :refer :all]))

(def player-character 
  "The character that represents the player."
  "@")

(defn find-player [grid]
  "Returns the (X Y) position of the player."
  (let [row (first (filter #(> (.indexOf % player-character) -1) grid))]
    (list (.indexOf row player-character) (.indexOf grid row))))

(defn check-player-move [grid player-position direction]
  "Returns the direction if the player can move that way, nil otherwise."
  (let [move-amount 
        (get-in grid (reverse
                      (get-next-coordinate grid player-position direction)))
        moves-made ((traverse-grid grid player-position direction) :moves-made)]
    (when (and (not= move-amount 0)
               (= move-amount moves-made))
      direction)))

(defn get-player-moves [grid]
  "Returns keywords for the directions in which a player can move."
  (let [player-position (find-player grid)]
    (remove nil?
            (list
             (check-player-move grid player-position :northwest)
             (check-player-move grid player-position :north)
             (check-player-move grid player-position :northeast)
             (check-player-move grid player-position :west)
             (check-player-move grid player-position :southwest)
             (check-player-move grid player-position :south)
             (check-player-move grid player-position :southeast)
             (check-player-move grid player-position :east)))))

(defn move-player [grid direction]
  (if (not (check-player-move grid (find-player grid) direction))
    grid
    (let [player-position (find-player grid)
          x (first player-position)
          y (second player-position)
          grid-with-replaced-player (assoc-in grid (reverse player-position) 0)
          new-position ((traverse-grid grid (find-player grid) direction)
                        :position)
          grid-with-new-player-position (assoc-in
                                         grid-with-replaced-player
                                         (reverse new-position)
                                         player-character)
          grid-with-zeroed-path (zero-path-between
                                 grid-with-new-player-position
                                 direction
                                 player-position
                                new-position)]
      grid-with-zeroed-path)))
