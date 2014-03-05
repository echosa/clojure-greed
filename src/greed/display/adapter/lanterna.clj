(ns greed.display.adapter.lanterna
  (:require [clojure.core.typed :as t]
            [greed.types :as t2]
            [lanterna.screen :as s]))

(t/ann ^:no-check scr t/Any)
(def scr (s/get-screen))

(t/ann ^:no-check start [ -> nil])
(defn start
  "Setup and start the adapter."
  []
  (s/start scr))

(t/ann ^:no-check stop [ -> nil])
(defn stop
  "Stop the adapter."
  []
  (s/stop scr))

(t/ann ^:no-check clear-screen [ -> nil])
(defn clear-screen
  "Clear the scren."
  []
  (s/clear scr))

(t/ann ^:no-check draw-screen [ -> nil])
(defn draw-screen
  "Actually draws the screen."
  []
  (s/redraw scr))

(t/ann ^:no-check place-cursor [t2/Point -> nil])
(defn place-cursor
  "Place the cursor at the given coordinates."
  [position]
  (s/move-cursor scr (first position) (second position)))

(t/ann ^:no-check wait-for-key [ -> nil])
(defn wait-for-key
  "Wait until the user presses a key, then return the key that was pressed."
  []
  (s/get-key-blocking scr))

(t/ann ^:no-check print-string-at-location
       (t/IFn [String t2/ColorMap t2/Point -> nil]
              [String t2/Point -> nil]))
(defn print-string-at-location
  "Prints the given string at the given x and y positions."
  ([string color point]
   (let [[x y] point]
     (s/put-string scr x y string color)))

  ([string point]
   (let [[x y] point]
     (print-string-at-location string {:fg :default} [x y]))))
