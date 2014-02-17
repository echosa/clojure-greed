(ns greed.display
  (:require [greed.player :refer :all]
            [lanterna.screen :as s]))

(defn get-grid-item-string
  "Given an item in the grid vector, returns the appropriate string to display."
  [grid x y]
  (let [thing (get-in grid [y x])]
    (if (= thing 0)
      " "
      (print-str thing))))

(defn place-cursor
  "Place the cursor on the given coordinates of the grid."
  [position scr]
  (s/move-cursor scr (first position) (second position)))

(defn print-grid
  "Prints the grid to the screen."
  [grid scr]
  (doseq [x (range (count (first grid))) y (range (count grid))]
    (s/put-string scr x y (get-grid-item-string grid x y)))
  (place-cursor (find-player grid) scr)
  (s/redraw scr))

(defn print-message
  "Prints a message below the grid."
  [message grid scr]
  (s/put-string scr 0 (inc (count grid)) message)
  (s/redraw scr))

