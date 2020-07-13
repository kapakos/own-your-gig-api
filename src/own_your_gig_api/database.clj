(ns own-your-gig-api.database
  (:require [toucan.db :as db]
            [toucan.models :as models]
            [own-your-gig-api.models.types :as types]
            [environ.core :refer [env]]))

(defn configure-toucan
  []
  (db/set-default-db-connection!
   (clojure.edn/read-string (env :database)))
  (models/set-root-namespace! 'own-your-gig-api.models)
  (types/add-types))
