(defproject own-your-gig-api "0.1.0-SNAPSHOT"
  :description "Own your gig API"
  :url "http://ownyourgig.com/"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 
                 ; Web
                 [prismatic/schema "1.1.12"]
                 [metosin/compojure-api "2.0.0-alpha31"]
                 [ring/ring-jetty-adapter "1.8.1"]
                 
                 ; Database
                 [toucan "1.15.1"]
                 [org.postgresql/postgresql "42.2.14"]
                 [buddy/buddy-hashers "1.4.0"]

                 ; config
                 [environ "1.2.0"]

                 ; java time
                 [clojure.java-time "0.3.2"]
                 ]
  :plugins [[lein-environ "1.2.0"]]
  :main ^:skip-aot own-your-gig-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev [:project/dev :profiles/dev]
             :test [:project/test :profiles/test]})
