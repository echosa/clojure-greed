(ns greed.grid
  (:require [clojure.core.typed :as t]
            [greed.types :as t2]
            [greed.coords :as c]))

(t/ann ^:no-check zero-position [t2/Grid t2/Point -> t2/Grid])
(defn zero-position
  "Place a 0 in the position on the grid."
  [grid position]
  (assoc-in grid (reverse position) 0))

(t/ann rand-grid-item [-> t/Num])
(defn rand-grid-item
  "Returns a random nubmer between 1 and 9."
  []
  (let [num (rand-int 10)]
    (if (= 0 num)
      (rand-grid-item)
      num)))

(t/ann generate-grid [t/Int t/Int -> t2/Grid])
(defn generate-grid
  "Generates a random grid of size HEIGHT x WIDTH, and randomly places player."
  [height width]
  (into []
        (repeatedly height
                    (fn [] 
                      (into []
                            (repeatedly width
                                        (fn []
                                          (rand-grid-item))))))))

(t/ann traverse-grid
       (t/IFn [t2/Grid t2/Point t2/Direction -> t2/Path]
              [t2/Grid t2/Point t2/Direction t/Num t/Num (t/Vec t2/Point) -> t2/Path]))
(defn traverse-grid 
  "Traverses the gride in the given direction."
  ([grid position direction]
   (let [thing-at-first-move
         (get-in grid (reverse (c/get-next-coordinate grid position direction)))
         max-moves (if (number? thing-at-first-move) thing-at-first-move 0)]
     (traverse-grid grid position direction max-moves 0 [])))

  ([grid position direction max-moves moves-made positions-traversed]
   (let [next-move (c/get-next-coordinate grid position direction)]
     (if (and next-move
              (not= (get-in grid (reverse next-move)) 0)
              (< moves-made max-moves))
       (traverse-grid grid next-move direction max-moves (+ moves-made 1)
                      (conj positions-traversed next-move))
       {:position position :moves-made moves-made
        :positions-traversed positions-traversed}))))

(t/ann zero-path-between [t2/Grid t2/Direction t2/Point t2/Point -> t2/Grid])
(defn zero-path-between
  "Traverses the path between start and end and changes each position to 0."
  [grid direction start end]
  (let [next (c/get-next-coordinate grid start direction)]
    (if (and next (not= next end))
      (zero-path-between (zero-position grid next) direction next end)
      grid)))


(t/ann get-number-of-cleared-spaces [t2/Grid -> t/Int])
(defn get-number-of-cleared-spaces
  "Returns the number of cleared spaces in the grid."
  [grid]
  (inc (reduce (t/fn [cleared :- t/Int, row :- t2/Row]
                 (let [result (inc (reduce (t/fn [row-cleared :- t/Int,
                                                  grid-item :- t/Any]
                                             (if (or (= grid-item 0)
                                                     (= grid-item "@"))
                                               (inc row-cleared)
                                               row-cleared))
                                           -1 row))]
                   (+ cleared result)))
               -1 grid)))

(t/ann calculate-score [t2/Grid -> Double])
(defn calculate-score
  "Calculates the user's score as percentage of the grid cleared."
  [grid]
  (let [height (count grid)
        width (count (first grid))
        area (* height width)
        num-cleared (get-number-of-cleared-spaces grid)]
    (double (* 100 (/ num-cleared area)))))
