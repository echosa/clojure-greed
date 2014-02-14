(ns greed.coords)

(defn get-next-coordinate-northwest [grid x y]
  (when-not (or (<= x 0)
                (<= y 0))
    (list (- x 1) (- y 1))))

(defn get-next-coordinate-north [grid x y]
  (when-not (<= y 0)
    (list x (- y 1))))

(defn get-next-coordinate-northeast [grid x y]
  (when-not (or (>= x (- (count (first grid)) 1))
                (<= y 0))
    (list (+ x 1) (- y 1))))

(defn get-next-coordinate-east [grid x y]
  (when-not (>= x (- (count (first grid)) 1))
    (list (+ x 1) y)))

(defn get-next-coordinate-southeast [grid x y]
  (when-not (or (>= x (- (count (first grid)) 1))
                (>= y (- (count grid) 1)))
    (list (+ x 1) (+ y 1))))

(defn get-next-coordinate-south [grid x y]
  (when-not (>= y (- (count grid) 1))
    (list x (+ y 1))))

(defn get-next-coordinate-southwest [grid x y]
  (when-not (or (<= x 0)
                (>= y (- (count grid) 1)))
    (list (- x 1) (+ y 1))))

(defn get-next-coordinate-west [grid x y]
  (when-not (<= x 0)
    (list (- x 1) y)))

(defn get-next-coordinate [grid current-position direction]
  (let [x (first current-position)
        y (nth current-position 1)]
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
