(ns greed.player
  (:require [clojure.core.typed :as t]
            [greed.types :as t2]
            [greed.grid :as g]
            [greed.coords :as c])
  (:import (clojure.lang APersistentVector)))

(t/ann player-character String)
(def player-character 
  "The character that represents the player."
  "@")

(t/ann ^:no-check place-player-at-position [t2/Grid t2/Point -> t2/Grid])
(defn place-player-at-position
  "Place the player marker at the position on the grid."
  [grid position]
  (assoc-in grid (reverse position) player-character))

(t/ann place-character-on-grid [t2/Grid -> t2/Grid])
(defn place-character-on-grid
  "Places the player's character on a random spot on the grid."
  [grid]
  (let [height (count grid)
        width (count (first grid))]
    (place-player-at-position grid [(rand-int width) (rand-int height)])))

(t/ann get-player-row [t2/Grid -> (t/Option t2/Row)])
(defn- get-player-row
  "Returns the row the player is in."
  [grid]
  (first
   (filter
    (t/fn [row :- t2/Row]
      (let [^APersistentVector row (t/ann-form row t2/Row) 
            _ (assert (instance? APersistentVector row))]
        (> (.indexOf row player-character) -1)))
    grid)))

(t/ann find-player [t2/Grid -> t2/Point])
(defn find-player
  "Returns the (X Y) position of the player."
  [grid]
  (let [^APersistentVector grid (t/ann-form grid t2/Grid)
        _ (assert (instance? APersistentVector grid))
        player-row (get-player-row grid)
        ^APersistentVector player-row (t/ann-form player-row (t/Option t2/Row))
        _ (assert (instance? APersistentVector player-row))]
    [(.indexOf ^APersistentVector player-row player-character)
     (.indexOf ^APersistentVector grid player-row)]))

(t/ann check-player-move [t2/Grid t2/Point t2/Direction -> (t/Option t2/Direction)])
(defn check-player-move
  "Returns the direction if the player can move that way, nil otherwise."
  [grid player-position direction]
  (let [move-amount 
        (get-in grid (reverse
                      (c/get-next-coordinate grid player-position direction)))
        moves-made (:moves-made (g/traverse-grid grid player-position direction))]
    (when (and (not= move-amount 0)
               (= move-amount moves-made))
      direction)))

(t/ann get-player-moves [t2/Grid -> (t/Vec t2/Direction)])
(defn get-player-moves
  "Returns keywords for the directions in which a player can move."
  [grid]
  (let [player-position (find-player grid)]
    (filterv (t/pred t2/Direction) [(check-player-move grid player-position :northwest)
                                    (check-player-move grid player-position :north)
                                    (check-player-move grid player-position :northeast)
                                    (check-player-move grid player-position :west)
                                    (check-player-move grid player-position :southwest)
                                    (check-player-move grid player-position :south)
                                    (check-player-move grid player-position :southeast)
                                    (check-player-move grid player-position :east)])))

(t/ann move-player [t2/Grid t2/Direction -> t2/Grid])
(defn move-player
  "Move the player on the grid in the given direction."
  [grid direction]
  (if-not (check-player-move grid (find-player grid) direction)
    grid
    (let [player-position (find-player grid)
          x (first player-position)
          y (second player-position)
          grid-with-replaced-player (g/zero-position grid player-position) 
          new-position (:position (g/traverse-grid grid (find-player grid) direction))
          grid-with-new-player-position (place-player-at-position
                                         grid-with-replaced-player
                                         new-position)
          grid-with-zeroed-path (g/zero-path-between
                                 grid-with-new-player-position
                                 direction
                                 player-position
                                 new-position)]
      grid-with-zeroed-path)))


(t/ann grid-position-is-part-of-valid-move
       (t/IFn [t2/Grid t2/Point t2/Direction -> (t/Option t/Bool)]
              [t2/Grid t2/Point -> (t/Option t/Bool)]))
(defn grid-position-is-part-of-valid-move
  "Returns true of the given position is part of a valid move, false otherwise."
  ([grid target direction]
   (let [player-position (find-player grid)
         positions-traversed 
         (:positions-traversed
          (g/traverse-grid grid player-position direction))
         ^APersistentVector positions-traversed (t/ann-form positions-traversed 
                                                            (t/Option (t/Vec t/Any)))
         _ (assert (instance? APersistentVector positions-traversed))]
     (and 
      (check-player-move grid player-position direction)
      (< -1 (.indexOf positions-traversed target)))))

  ([grid target]
   (or (grid-position-is-part-of-valid-move grid target :northwest)
       (grid-position-is-part-of-valid-move grid target :north)
       (grid-position-is-part-of-valid-move grid target :northeast)
       (grid-position-is-part-of-valid-move grid target :east)
       (grid-position-is-part-of-valid-move grid target :southeast)
       (grid-position-is-part-of-valid-move grid target :south)
       (grid-position-is-part-of-valid-move grid target :southwest)
       (grid-position-is-part-of-valid-move grid target :west))))
