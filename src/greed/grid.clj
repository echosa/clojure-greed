(ns greed.grid
  (:require [greed.coords :refer :all]))

(defn get-thing-at-grid-position [grid position]
  "Returns whatever is at (X Y) in the grid or nil if bad (X Y) is given."
  (let [x (first position)
        y (try
            (nth position 1)
            (catch NullPointerException e nil))]
    (when y
      (try
        (nth (nth grid y) x)                        
        (catch IndexOutOfBoundsException e nil)))))

(defn set-thing-at-grid-position [grid position thing]
  "Sets the position of grid to THING, then returns the new grid."
  (let [x (first position)
        y (nth position 1)]
    (into (conj (subvec grid 0 y)
                (into (conj (subvec (first (subvec grid y (inc y))) 0 x)
                            thing)
                      (subvec (first (subvec grid y (inc y))) (inc x))))
          (subvec grid (inc y)))))

(defn traverse-grid-in-direction 
  "Traverses the gride in the given direction."
  ([grid position direction]
     (let [thing-at-first-move (get-thing-at-grid-position
                                grid
                                (get-next-coordinate grid position direction))
           max-moves (if thing-at-first-move thing-at-first-move 0)]
       (traverse-grid-in-direction grid position direction max-moves 0)))

  ([grid position direction max-moves moves-made]
     (let [next-move (get-next-coordinate grid position direction)]
       (if (and next-move
                (not= (get-thing-at-grid-position grid next-move) 0)
                (< moves-made max-moves))
         (traverse-grid-in-direction grid next-move direction max-moves
                                     (+ moves-made 1))
         {:position position :moves-made moves-made}))))

(defn zero-path-between [grid direction start end]
  "Traverses the path between start and end and changes each position to 0."
  (let [next (get-next-coordinate grid start direction)]
    (if (not= next end)
      (zero-path-between
       (set-thing-at-grid-position grid next 0)
       direction next end)
      grid)))
