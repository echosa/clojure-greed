(ns greed.player
  (:require [greed.grid :refer :all]
            [greed.coords :refer :all]))

(def player-character 
  "The character that represents the player."
  "@")

(defn place-character-on-grid
  "Places the player's character on a random spot on the grid."
  [grid]
  (let [height (count grid)
        width (count (first grid))]
    (assoc-in grid (list (rand-int height) (rand-int width)) player-character)))

(defn find-player
  "Returns the (X Y) position of the player."
  [grid]
  (let [row (first (filter #(> (.indexOf % player-character) -1) grid))]
    (list (.indexOf row player-character) (.indexOf grid row))))

(defn check-player-move
  "Returns the direction if the player can move that way, nil otherwise."
  [grid player-position direction]
  (let [move-amount 
        (get-in grid (reverse
                      (get-next-coordinate grid player-position direction)))
        moves-made ((traverse-grid grid player-position direction) :moves-made)]
    (when (and (not= move-amount 0)
               (= move-amount moves-made))
      direction)))

(defn get-player-moves
  "Returns keywords for the directions in which a player can move."
  [grid]
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

(defn move-player
  "Move the player on the grid in the given direction."
  [grid direction]
  (if-not (check-player-move grid (find-player grid) direction)
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

(defn grid-position-is-part-of-valid-move
  "Returns true of the given position is part of a valid move, false otherwise."
  ([grid target direction]
     (let [player-position (find-player grid)]
       (and 
        (check-player-move grid player-position direction)
        (< -1 (.indexOf (:positions-traversed
                         (traverse-grid grid player-position direction))
                        target)))))

  ([grid target]
     (or (grid-position-is-part-of-valid-move grid target :northwest)
         (grid-position-is-part-of-valid-move grid target :north)
         (grid-position-is-part-of-valid-move grid target :northeast)
         (grid-position-is-part-of-valid-move grid target :east)
         (grid-position-is-part-of-valid-move grid target :southeast)
         (grid-position-is-part-of-valid-move grid target :south)
         (grid-position-is-part-of-valid-move grid target :southwest)
         (grid-position-is-part-of-valid-move grid target :west))))

