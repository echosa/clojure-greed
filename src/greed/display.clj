(ns greed.display
  (:require [clojure.core.typed :as t]
            [greed.types :as t2]
            [greed.player :as p]
            [greed.grid :as g]
            [greed.display.adapter.lanterna :as adapter]))

(t/ann start-display [ -> nil])
(defn start-display
  "Start the display."
  []
  (adapter/start))

(t/ann stop-display [ -> nil])
(defn stop-display
  "Stop the display."
  []
  (adapter/stop))

(t/ann get-grid-item [t2/Grid t2/Point t/Bool -> t2/StringFormat])
(defn get-grid-item
  "Given an item in the grid vector, returns the string and color to display."
  [grid point show-moves]
  (let [[x y] point
        thing (get-in grid [y x])
        highlight (if (and
                       show-moves
                       (p/grid-position-is-part-of-valid-move grid [x y]))
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
      "@" {:string "@" :color {:fg :white}})))

(t/ann print-grid [t2/Grid t/Bool -> nil])
(defn print-grid
  "Prints the grid to the screen."
  [grid show-moves]
  (t/doseq [x :- t/Num (range (count (first grid)))
            y :- t/Num (range (count grid))]
    (let [point [x y]
          {:keys [string color]} (get-grid-item grid point show-moves)]
      (adapter/print-string-at-location string color point)))
  (adapter/place-cursor (p/find-player grid)))

(t/ann print-keys [t2/Grid -> nil])
(defn print-keys
  "Prints the keybindings for the game on the screen."
  [grid]
  (adapter/print-string-at-location "Controls:" [1 (count grid)])
  (adapter/print-string-at-location "Y K U" [1 (inc (count grid))])
  (adapter/print-string-at-location " \\|/" [1 (+ (count grid) 2)])
  (adapter/print-string-at-location "H- -L" [1 (+ (count grid) 3)])
  (adapter/print-string-at-location " /|\\" [1 (+ (count grid) 4)])
  (adapter/print-string-at-location "B J N" [1 (+ (count grid) 5)])
  (adapter/print-string-at-location "P - Show moves (can cause extreme lag!)"
                                    [1 (+ (count grid) 6)])
  (adapter/print-string-at-location "Q - Quit" [1 (+ (count grid) 7)]))

(t/ann print-score [t2/Grid -> nil])
(defn print-score
  "Prints the score to the screen."
  [grid]
  (adapter/print-string-at-location
   (str "Score: " (format "%.2f" (g/calculate-score grid)) "%  ")
   [15 (inc (count grid))]))

(t/ann print-screen [t2/Grid t/Bool -> nil])
(defn print-screen
  "Prints the game screen."
  [grid show-moves]
  (adapter/clear-screen)
  (print-grid grid show-moves)
  (print-keys grid)
  (print-score grid)
  (adapter/draw-screen))

(t/ann print-message [String t2/Grid -> nil])
(defn print-message
  "Prints a message below the grid."
  [message grid]
  (adapter/print-string-at-location message [15 (+ (count grid) 3)])
  (adapter/draw-screen))
