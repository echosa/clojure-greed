(ns greed.grid
  (:require [greed.coords :refer :all]))

(defn traverse-grid 
  "Traverses the gride in the given direction."
  ([grid position direction]
     (let [thing-at-first-move (get-in
                                grid
                                (reverse
                                 (get-next-coordinate grid position direction)))
           max-moves (if thing-at-first-move thing-at-first-move 0)]
       (traverse-grid grid position direction max-moves 0)))

  ([grid position direction max-moves moves-made]
     (let [next-move (get-next-coordinate grid position direction)]
       (if (and next-move
                (not= (get-in grid (reverse next-move)) 0)
                (< moves-made max-moves))
         (traverse-grid grid next-move direction max-moves (+ moves-made 1))
         {:position position :moves-made moves-made}))))

(defn zero-path-between [grid direction start end]
  "Traverses the path between start and end and changes each position to 0."
  (let [next (get-next-coordinate grid start direction)]
    (if (not= next end)
      (zero-path-between (assoc-in grid (reverse next) 0) direction next end)
      grid)))
