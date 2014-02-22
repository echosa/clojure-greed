(ns greed.input
  (:require [greed.display.adapter.lanterna :as adapter]))

(defn wait-for-key
  "Wait until the user presses a key, then return the key that was pressed."
  []
  (adapter/wait-for-key))
