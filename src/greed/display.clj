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
  [grid x y show-moves]
  (let [thing (get-in grid [y x])
        highlight (if (and
                       show-moves
                       (grid-position-is-part-of-valid-move grid `(~x ~y)))
                    :white
                    :default)]
    (condp = thing
      0 {:string " " :color {:fg :white :bg highlight}}
      1 {:string "1" :color {:fg :yellow :bg highlight}}
      2 {:string "2" :color {:fg :red :bg highlight}}
      3 {:string "3" :color {:fg :green :bg highlight}}
      4 {:string "4" :color {:fg :cyan :bg highlight}}
      5 {:string "5" :color {:fg :magenta :bg highlight}}
      6 {:string "6" :color {:fg :yellow :bg highlight}}
      7 {:string "7" :color {:fg :red :bg highlight}}
      8 {:string "8" :color {:fg :green :bg highlight}}
      9 {:string "9" :color {:fg :cyan :bg highlight}}
      "@" {:string "@" :color {:fg :white}}
      nil)))

(defn print-grid
  "Prints the grid to the screen."
  [grid show-moves scr]
  (doseq [x (range (count (first grid))) y (range (count grid))]
    (let [{:keys [string color]} (get-grid-item grid x y show-moves)]
      (s/put-string scr x y string color)))
  (place-cursor (find-player grid) scr))

(defn print-keys
  "Prints the keybindings for the game on the screen."
  [grid scr]
  (s/put-string scr 1 (count grid) "Controls:")
  (s/put-string scr 1 (inc (count grid)) "Y K U")
  (s/put-string scr 1 (+ (count grid) 2) " \\|/")
  (s/put-string scr 1 (+ (count grid) 3) "H- -L")
  (s/put-string scr 1 (+ (count grid) 4) " /|\\")
  (s/put-string scr 1 (+ (count grid) 5) "B J N")
  (s/put-string scr 1 (+ (count grid) 6) "P - Show moves (can cause extreme lag!)")
  (s/put-string scr 1 (+ (count grid) 7) "Q - Quit"))

(defn print-score
  "Prints the score to the screen."
  [grid scr]
  (s/put-string scr 15 (inc (count grid))
                (str "Score: " (format "%.2f" (calculate-score grid)) "%  ")))

(defn print-screen
  "Prints the game screen."
  [grid show-moves scr]
  (print-grid grid show-moves scr)
  (print-keys grid scr)
  (print-score grid scr)
  (s/redraw scr))

(defn print-message
  "Prints a message below the grid."
  [message grid scr]
  (s/put-string scr 15 (+ (count grid) 3) message)
  (s/redraw scr))
