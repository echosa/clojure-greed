(ns greed.types
  (:require [clojure.core.typed :as t]))

(t/defalias Row (t/Vec t/Any))
(t/defalias Grid (t/Vec Row))
(t/defalias Point '[t/Num t/Num])
(t/defalias Direction (t/U ':north
                           ':northeast
                           ':east
                           ':southeast
                           ':south
                           ':southwest
                           ':west
                           ':northwest))
(t/defalias Path (t/HMap :mandatory {:position Point
                                     :moves-made t/Num
                                     :positions-traversed (t/Vec Point)}))
(t/defalias ColorMap (t/HMap :mandatory {:fg t/Keyword}
                             :optional {:bg t/Keyword}
                             :complete? true))
(t/defalias StringFormat (t/HMap :mandatory {:string String
                                             :color ColorMap}
                                 :complete? true)) 
