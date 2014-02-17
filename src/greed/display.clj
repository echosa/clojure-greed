(ns greed.display
  (:require [greed.player :refer :all]
            [greed.grid :refer :all]
            [lanterna.screen :as s]))

(defn place-cursor
  "Place the cursor on the given coordinates of the grid."
  [position scr]
  (s/move-cursor scr (first position) (second position)))

(defn get-grid-item
  "Given an item in the grid vector, returns the string and color to display."
  [grid x y]
  (let [thing (get-in grid [y x])]
    (condp = thing
      0 {:string " " :color {:fg :white}}
      1 {:string "1" :color {:fg :yellow}}
      2 {:string "2" :color {:fg :red}}
      3 {:string "3" :color {:fg :green}}
      4 {:string "4" :color {:fg :cyan}}
      5 {:string "5" :color {:fg :magenta}}
      6 {:string "6" :color {:fg :yellow}}
      7 {:string "7" :color {:fg :red}}
      8 {:string "8" :color {:fg :green}}
      9 {:string "9" :color {:fg :cyan}}
      "@" {:string "@" :color {:fg :white}}
      nil)))

(defn print-grid
  "Prints the grid to the screen."
  [grid scr]
  (doseq [x (range (count (first grid))) y (range (count grid))]
    (let [{:keys [string color]} (get-grid-item grid x y)]
      (s/put-string scr x y string color)))
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
  (s/put-string scr 15 (inc (count grid)) (str "Score: "
                                               (calculate-score grid)
                                               "%      ")))

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
