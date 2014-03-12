(ns greed.coords
  (:require [clojure.core.typed :refer :all])
  (:import [clojure.lang IPersistentVector]))

(ann get-next-coordinate-northwest
     [(IPersistentVector (IPersistentVector Any)) Number Number
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-northwest [grid x y]
  (when (and (> x 0) (> y 0))
    [(- x 1) (- y 1)]))

(ann get-next-coordinate-north
     [(IPersistentVector (IPersistentVector Any)) Number Number
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-north [grid x y]
  (when (> y 0)
    [x (- y 1)]))

(ann get-next-coordinate-northeast
     [(IPersistentVector (IPersistentVector Any)) Number Number
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-northeast [grid x y]
  (when (and (< x (- (count (first grid)) 1))
             (> y 0))
    [(+ x 1) (- y 1)]))

(ann get-next-coordinate-east
     [(IPersistentVector (IPersistentVector Any)) Number Number
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-east [grid x y]
  (when (< x (- (count (first grid)) 1))
    [(+ x 1) y]))

(ann get-next-coordinate-southeast
     [(IPersistentVector (IPersistentVector Any)) Number Number
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-southeast [grid x y]
  (when (and (< x (- (count (first grid)) 1))
             (< y (- (count grid) 1)))
    [(+ x 1) (+ y 1)]))

(ann get-next-coordinate-south
     [(IPersistentVector (IPersistentVector Any)) Number Number
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-south [grid x y]
  (when (< y (- (count grid) 1))
    [x (+ y 1)]))

(ann get-next-coordinate-southwest
     [(IPersistentVector (IPersistentVector Any)) Number Number
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-southwest [grid x y]
  (when (and (> x 0)
             (< y (- (count grid) 1)))
    [(- x 1) (+ y 1)]))

(ann get-next-coordinate-west
     [(IPersistentVector (IPersistentVector Any)) Number Number
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-west [grid x y]
  (when (> x 0)
    [(- x 1) y]))

(ann get-next-coordinate
     [(IPersistentVector (IPersistentVector Any)) (Vector* Number Number) Symbol
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate [grid current-position direction]
  (let [x (or (first current-position) -1)
        y (or (second current-position) -1)]
    (condp = direction
      :northwest (get-next-coordinate-northwest grid x y)
      :north (get-next-coordinate-north grid x y)
      :northeast (get-next-coordinate-northeast grid x y)
      :east (get-next-coordinate-east grid x y)
      :southeast (get-next-coordinate-southeast grid x y)
      :south (get-next-coordinate-south grid x y)
      :southwest (get-next-coordinate-southwest grid x y)
      :west (get-next-coordinate-west grid x y)
      nil)))
