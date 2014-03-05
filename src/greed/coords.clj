(ns greed.coords
  (:require [clojure.core.typed :refer :all])
  (:import [clojure.lang ISeq IPersistentList]))

(ann get-next-coordinate-northwest
     [(ISeq (ISeq Any)) (Option Number) (Option Number)
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-northwest [grid x y]
  (when (and x y grid
             (> x 0) (> y 0))
    [(- x 1) (- y 1)]))

(ann get-next-coordinate-north
     [(ISeq (ISeq Any)) (Option Number) (Option Number)
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-north [grid x y]
  (when (and x y grid
             (> y 0))
    [x (- y 1)]))

(ann get-next-coordinate-northeast
     [(ISeq (ISeq Any)) (Option Number) (Option Number)
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-northeast [grid x y]
  (when (and x y grid
             (< x (- (count (first grid)) 1))
             (> y 0))
    [(+ x 1) (- y 1)]))

(ann get-next-coordinate-east
     [(ISeq (ISeq Any)) (Option Number) (Option Number)
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-east [grid x y]
  (when (and grid x y
             (< x (- (count (first grid)) 1)))
    [(+ x 1) y]))

(ann get-next-coordinate-southeast
     [(ISeq (ISeq Any)) (Option Number) (Option Number)
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-southeast [grid x y]
  (when (and grid x y
             (< x (- (count (first grid)) 1))
             (< y (- (count grid) 1)))
    [(+ x 1) (+ y 1)]))

(ann get-next-coordinate-south
     [(ISeq (ISeq Any)) (Option Number) (Option Number)
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-south [grid x y]
  (when (and grid x y
             (< y (- (count grid) 1)))
    [x (+ y 1)]))

(ann get-next-coordinate-southwest
     [(ISeq (ISeq Any)) (Option Number) (Option Number)
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-southwest [grid x y]
  (when (and grid x y
             (> x 0)
             (< y (- (count grid) 1)))
    [(- x 1) (+ y 1)]))

(ann get-next-coordinate-west
     [(ISeq (ISeq Any)) (Option Number) (Option Number)
      ->
      (Option (Vector* Number Number))])
(defn get-next-coordinate-west [grid x y]
  (when-not (or (nil? x) (nil? y) (<= x 0))
    [(- x 1) y]))

(ann get-next-coordinate
     [(ISeq (ISeq Any)) (IPersistentList Number) Symbol -> (Option (Vector* Number Number))])
(defn get-next-coordinate [grid current-position direction]
  (let [x (first current-position)
        y (second current-position)]
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
