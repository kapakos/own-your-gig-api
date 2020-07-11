(ns own-your-gig-api.database
  (:require [toucan.db :as db]
    [toucan.models :as models]
    [environ.core :refer [env]])
  (:import [org.postgresql.util PGobject]))

(defn- pg-object-fn
  [pg-type]
  (fn [value]
    (doto (PGobject.)
      (.setType pg-type)
      (.setValue (name value)))))

(defn configure-toucan
  []
  (db/set-default-db-connection!
    (clojure.edn/read-string (env :database)))
  (models/set-root-namespace! 'own-your-gig-api.models))

