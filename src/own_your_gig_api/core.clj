(ns own-your-gig-api.core
  (:gen-class))
(require  '[toucan.db :as db]
          '[toucan.models :as models]
          '[ring.adapter.jetty :refer [run-jetty]]
          '[compojure.api.sweet :refer [routes]]
          '[own-your-gig-api.user :refer [app]]
          '[own-your-gig-api.database :refer [configure-toucan]]
          '[java-time :refer [offset-time local-date instant offset-date-time local-date-time zoned-date-time]]
          '[java-time.format :as jtf])


(defn -main
  "Starting own your gig API"
  [& args]
  (configure-toucan)
  (run-jetty app {:port 3000}))
