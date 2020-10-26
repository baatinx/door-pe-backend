(defproject doorpe.backend.server.core "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.novemberain/monger "3.5.0"]
                 [ring/ring-core "1.8.1"]
                 [ring/ring-jetty-adapter "1.8.1"]
                 [ring/ring-mock "0.3.2"]
                 [compojure "1.6.2"]
                 [ring-cors "0.1.13"]
                 [metosin/muuntaja "0.6.7"]
                 [metosin/jsonista "0.2.7"]
                 [ring/ring-json "0.5.0"]
                 [clj-http "3.10.3"]
                 [clj-time "0.15.2"]
                 [tick "0.4.26-alpha"]

                ;;  [ring/ring-defaults "0.3.2"]
                ;;  [org.clojure/data.codec "0.1.1"]

                 [crypto-random "1.2.0"]
                 [buddy/buddy-core "1.6.0"]
                 [buddy/buddy-auth "2.2.0"]
                 [buddy/buddy-hashers "1.4.0"]
                 [buddy/buddy-sign "3.1.0"]
                 [com.draines/postal "2.0.3"]]
  :main ^:skip-aot doorpe.backend.server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
