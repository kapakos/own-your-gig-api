(ns own-your-gig-api.handler.task
  (:require [own-your-gig-api.schemas.task :refer [TaskRequestSchema]]
            [own-your-gig-api.models.task :refer [Task]]
            [ring.util.response :as ring-resp] 
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [toucan.db :as db]))

(def common-interceptors [(body-params/body-params) http/json-body])

(defn get-tasks-handler
  [request]
  (->> (db/select Task)
       ring-resp/response))

(def routes #{["/task" :get (conj common-interceptors `get-tasks-handler)]})
