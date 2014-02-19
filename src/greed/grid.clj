(ns greed.grid
  (:require [greed.coords :refer :all]))

(defn rand-grid-item
  "Returns a random nubmer between 1 and 9."
  []
  (let [num (rand-int 10)]
    (if (= 0 num)
      (rand-grid-item)
      num)))

(defn generate-grid
  "Generates a random grid of size HEIGHT x WIDTH, and randomly places player."
  [height width]
  (into []
         (repeatedly height (fn [] 
                              (into []
                                    (repeatedly width (fn []
                                                        (rand-grid-item))))))))

(defn traverse-grid 
  "Traverses the gride in the given direction."
  ([grid position direction]
     (let [thing-at-first-move
           (get-in grid (reverse (get-next-coordinate grid position direction)))
           max-moves (if thing-at-first-move thing-at-first-move 0)]
       (traverse-grid grid position direction max-moves 0 [])))

  ([grid position direction max-moves moves-made positions-traversed]
     (let [next-move (get-next-coordinate grid position direction)]
       (if (and next-move
                (not= (get-in grid (reverse next-move)) 0)
                (< moves-made max-moves))
         (traverse-grid grid next-move direction max-moves (+ moves-made 1)
                        (conj positions-traversed next-move))
         {:position position :moves-made moves-made
          :positions-traversed positions-traversed}))))

(defn zero-path-between
  "Traverses the path between start and end and changes each position to 0."
  [grid direction start end]
  (let [next (get-next-coordinate grid start direction)]
    (if (not= next end)
      (zero-path-between (assoc-in grid (reverse next) 0) direction next end)
      grid)))

(defn get-number-of-cleared-spaces
  "Returns the number of cleared spaces in the grid."
  [grid]
  (inc (reduce (fn [a b]
                 (let [result (inc (reduce (fn [x y] (if (or (= y 0) (= y "@"))
                                                       (inc x)
                                                       x))
                                           -1 b))]
                   (+ a result))) -1 grid)))

(defn calculate-score
  "Calculates the user's score as percentage of the grid cleared."
  [grid]
  (let [height (count grid)
        width (count (first grid))
        area (* height width)
        num-cleared (get-number-of-cleared-spaces grid)]
    (double (* 100 (/ num-cleared area)))))


