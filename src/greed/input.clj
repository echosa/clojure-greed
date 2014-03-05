(ns greed.input
  (:require [clojure.core.typed :as t]
            [greed.display.adapter.lanterna :as adapter]))

(t/ann wait-for-key [ -> nil])
(defn wait-for-key
  "Wait until the user presses a key, then return the key that was pressed."
  []
  (adapter/wait-for-key))
