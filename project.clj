(defproject greed "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clojure-lanterna "0.9.4"]]
  :plugins [[lein-ancient "0.5.4"]
            [lein-cloverage "1.0.2"]]
  :main ^:skip-aot greed.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
