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
  (place-cursor (find-player grid) scr))

(defn print-keys
  "Prints the keybindings for the game on the screen."
  [grid scr]
  (s/put-string scr 1 (inc (count grid)) "Controls:")
  (s/put-string scr 1 (+ (count grid) 3) "Y K U")
  (s/put-string scr 1 (+ (count grid) 4) " \\|/")
  (s/put-string scr 1 (+ (count grid) 5) "H- -L")
  (s/put-string scr 1 (+ (count grid) 6) " /|\\")
  (s/put-string scr 1 (+ (count grid) 7) "B J N"))

(defn print-score
  "Prints the score to the screen."
  [grid scr]
  (s/put-string scr 15 (inc (count grid)) "Score:"))

(defn print-screen
  "Prints the game screen."
  [grid scr]
  (print-grid grid scr)
  (print-keys grid scr)
  (print-score grid scr)
  (s/redraw scr))

(defn print-message
  "Prints a message below the grid."
  [message grid scr]
  (s/put-string scr 15 (+ (count grid) 3) message)
  (s/redraw scr))

;; 1 - orange
;; 2 - red
;; 3 - green/yellow
;; 4 - cyan
;; 5 - pink
;; 6 - light grey
;; 7 - bold orange?
;; 8 - dark grey
;; 9 - really light grey
;; @ - white
