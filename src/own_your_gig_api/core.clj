(ns own-your-gig-api.core
  (:gen-class))
(require  '[ring.adapter.jetty :refer [run-jetty]]
          '[own-your-gig-api.app :refer [app]]
          '[own-your-gig-api.database :refer [configure-toucan]])

(defn -main
  "Starting own your gig API"
  [& args]
  (configure-toucan)
  (run-jetty app {:port 3000}))
