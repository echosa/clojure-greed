(ns greed.display.adapter.lanterna
  (:require [lanterna.screen :as s]))

(def scr (s/get-screen))

(defn start
  "Setup and start the adapter."
  []
  (s/start scr))

(defn stop
  "Stop the adapter."
  []
  (s/stop scr))

(defn clear-screen
  "Clear the scren."
  []
  (s/clear scr))

(defn draw-screen
  "Actually draws the screen."
  []
  (s/redraw scr))

(defn place-cursor
  "Place the cursor at the given coordinates."
  [position]
  (s/move-cursor scr (first position) (second position)))

(defn wait-for-key
  "Wait until the user presses a key, then return the key that was pressed."
  []
  (s/get-key-blocking scr))

(defn print-string-at-location
  "Prints the given string at the given x and y positions."
  [string x y]
  (s/put-string scr x y string))

(defn print-string-with-color-at-location
  "Prints the given string with color at the given x and y positions."
  [string color x y]
  (s/put-string scr x y string color))
