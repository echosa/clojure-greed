(ns greed.grid
  (:require [greed.coords :refer :all]
            [clojure.core.typed :refer :all])
  (:import [clojure.lang IPersistentVector]))

(ann rand-grid-item
     [-> Number])
(defn rand-grid-item
  "Returns a random nubmer between 1 and 9."
  []
  (let [num (rand-int 10)]
    (if (= 0 num)
      (rand-grid-item)
      num)))

(ann generate-grid
     [AnyInteger AnyInteger
      -> 
      (Coll Any)])
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

(ann traverse-grid
     (Fn [(IPersistentVector (IPersistentVector Any)) (Vector* Number Number) Symbol
          ->
          (U (IPersistentVector (IPersistentVector Any))
             (HMap :mandatory {:position (Vector* Number Number)
                               :moves-made Number
                               :positions-traversed (IPersistentVector
                                                     (Vector* Number Number))}
                   :complete? true))]

         [(IPersistentVector (IPersistentVector Any)) (Vector* Number Number) Symbol
          Number Number (IPersistentVector (Vector* Number Number))
          ->
          (U (IPersistentVector (IPersistentVector Any))
             (HMap :mandatory {:position (Vector* Number Number)
                               :moves-made Number
                               :positions-traversed (IPersistentVector
                                                     (Vector* Number Number))}
                   :complete? true))]))
(defn traverse-grid 
  "Traverses the gride in the given direction."
  ([grid position direction]
     (let [thing-at-first-move
           (get-in grid (reverse (get-next-coordinate grid position direction)))
           max-moves (if (number? thing-at-first-move) thing-at-first-move 0)]
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

;; ignore because assoc-in is an work in progress in core.typed
(tc-ignore
 (ann zero-path-between
      [(IPersistentVector (IPersistentVector Any)) Symbol (Vector* Number Number)
       (Vector* Number Number)
       ->
       (IPersistentVector (IPersistentVector Any))])
 (defn zero-path-between
   "Traverses the path between start and end and changes each position to 0."
   [grid direction start end]
   (let [next (get-next-coordinate grid start direction)]
     (if (and next (not= next end))
       (zero-path-between (assoc-in grid (reverse next) 0) direction next end)
       grid))))

(ann get-number-of-cleared-spaces
     [(IPersistentVector (IPersistentVector Any))
      ->
      AnyInteger])
(defn get-number-of-cleared-spaces
  "Returns the number of cleared spaces in the grid."
  [grid]
  (inc (reduce (fn> [[cleared :- AnyInteger]
                     [row :- (IPersistentVector Any)]]
                 (let [result (inc (reduce (fn> [[row-cleared :- AnyInteger]
                                                [grid-item :- Any]]
                                             (if (or (= grid-item 0)
                                                     (= grid-item "@"))
                                               (inc row-cleared)
                                               row-cleared))
                                           -1 row))]
                   (+ cleared result)))
               -1 grid)))

(ann calculate-score
     [(IPersistentVector (IPersistentVector Any))
      ->
      Double])
(defn calculate-score
  "Calculates the user's score as percentage of the grid cleared."
  [grid]
  (let [height (count grid)
        width (count (first grid))
        area (* height width)
        num-cleared (get-number-of-cleared-spaces grid)]
    (double (* 100 (/ num-cleared area)))))
