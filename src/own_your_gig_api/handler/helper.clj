(ns own-your-gig-api.handler.helper
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [toucan.db :as db]))

(def common-interceptors [(body-params/body-params) http/json-body])

(defn db-select-entity
  "Will return a list of all Entities passed as parameter and assign it to the :result key in the context"
  [entity name]
  {:name name
   :enter
   (fn [context]
     (assoc context :result (db/select entity)))})
