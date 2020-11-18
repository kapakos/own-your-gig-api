(ns own-your-gig-api.handler.db_helper
  (:require [toucan.db :as db]))

(defn db-select-entity
  "Will return a list of all Entities passed as parameter and assign it to the :result key in the context"
  [entity name]
  {:name name
   :enter
   (fn [context]
     (assoc context :result (db/select entity)))})
