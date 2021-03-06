(defproject greed "0.1.0-SNAPSHOT"
  :description "This is a port of the DOS/BSD freeware game greed."
  :url "http://github.com/echosa/clojure-greed"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clojure-lanterna "0.9.4"]
                 [org.clojure/core.typed "0.2.84"]]
  :plugins [[lein-cloverage "1.0.2"]
            [lein-typed "0.3.5"]]
  :core.typed {:check [greed.core]}
  :main ^:skip-aot greed.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
