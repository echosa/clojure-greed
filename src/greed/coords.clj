(ns greed.coords
  (:require [clojure.core.typed :as t]
            [greed.types :as t2]))

(t/ann get-next-coordinate-northwest [t2/Grid t2/Point -> (t/Option t2/Point)])
(defn get-next-coordinate-northwest
  "Returns the next coordianate to the northwest from the given point, or nil."
  [grid point]
  (let [[x y] point]
    (when (and (> x 0) (> y 0))
      [(- x 1) (- y 1)])))

(t/ann get-next-coordinate-north [t2/Grid t2/Point -> (t/Option t2/Point)])
(defn get-next-coordinate-north
  "Returns the next coordianate to the north from the given point, or nil."
  [grid point]
  (let [[x y] point]
    (when (> y 0)
      [x (- y 1)])))

(t/ann get-next-coordinate-northeast [t2/Grid t2/Point -> (t/Option t2/Point)])
(defn get-next-coordinate-northeast
  "Returns the next coordianate to the northeast from the given point, or nil."
  [grid point]
  (let [[x y] point]
    (when (and (< x (- (count (first grid)) 1))
               (> y 0))
      [(+ x 1) (- y 1)])))

(t/ann get-next-coordinate-east [t2/Grid t2/Point -> (t/Option t2/Point)])
(defn get-next-coordinate-east
  "Returns the next coordianate to the east from the given point, or nil."
  [grid point]
  (let [[x y] point]
    (when (< x (- (count (first grid)) 1))
      [(+ x 1) y])))

(t/ann get-next-coordinate-southeast [t2/Grid t2/Point -> (t/Option t2/Point)])
(defn get-next-coordinate-southeast
  "Returns the next coordianate to the southeast from the given point, or nil."
  [grid point]
  (let [[x y] point]
    (when (and (< x (- (count (first grid)) 1))
               (< y (- (count grid) 1)))
      [(+ x 1) (+ y 1)])))

(t/ann get-next-coordinate-south [t2/Grid t2/Point -> (t/Option t2/Point)])
(defn get-next-coordinate-south
  "Returns the next coordianate to the south from the given point, or nil."
  [grid point]
  (let [[x y] point]
    (when (< y (- (count grid) 1))
      [x (+ y 1)])))

(t/ann get-next-coordinate-southwest [t2/Grid t2/Point -> (t/Option t2/Point)])
(defn get-next-coordinate-southwest
  "Returns the next coordianate to the southwest from the given point, or nil."
  [grid point]
  (let [[x y] point]
    (when (and (> x 0)
               (< y (- (count grid) 1)))
      [(- x 1) (+ y 1)])))

(t/ann get-next-coordinate-west [t2/Grid t2/Point -> (t/Option t2/Point)])
(defn get-next-coordinate-west
  "Returns the next coordianate to the west from the given point, or nil."
  [grid point]
  (let [[x y] point]
    (when (> x 0)
      [(- x 1) y])))

(t/ann get-next-coordinate [t2/Grid t2/Point t2/Direction -> (t/Option t2/Point)])
(defn get-next-coordinate [grid current-position direction]
  (let [x (or (first current-position) -1)
        y (or (second current-position) -1)
        point [x y]]
    (condp = direction
      :northwest (get-next-coordinate-northwest grid point)
      :north (get-next-coordinate-north grid point)
      :northeast (get-next-coordinate-northeast grid point)
      :east (get-next-coordinate-east grid point)
      :southeast (get-next-coordinate-southeast grid point)
      :south (get-next-coordinate-south grid point)
      :southwest (get-next-coordinate-southwest grid point)
      :west (get-next-coordinate-west grid point)
      nil)))
