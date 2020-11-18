(ns own-your-gig-api.handler.task
  (:import java.util.UUID)
  (:require [own-your-gig-api.schemas.task :refer [TaskRequestSchema]]
            [own-your-gig-api.models.task :refer [Task]]
            [own-your-gig-api.utils.string_util :as str] 
            [own-your-gig-api.handler.interceptors :refer [common-interceptors]]
            [own-your-gig-api.handler.db_helper :refer [db-select-entity]]
            [ring.util.response :as ring-resp] 
            [toucan.db :as db]))

(defn- uid->created
  [uid]
  (ring-resp/created (str "/api" "/task/") {:task_uid uid}))

(def list-interceptor
  {:name :list-release-interceptor
   :leave
   (fn [context]
     (if-let [result (:result context)]
         (assoc context :response (ring-resp/response result))
         context))})

(def db-insert-interceptor
  {:name :db-insert-interceptor
   :leave
   (fn [context]
     (if-let [task (:result context)]
       (->>
          (db/insert! Task task)
          :task_uid
          (uid->created)
          (assoc context :response))
       context))})

(def create-task-interceptor
  {:name :create-task-interceptor
   :enter
   (fn [context]
     (let [task (get-in context [:request :json-params])]
       (->> 
         (update task :story_uid str/str->uuid)
         (assoc context :result))))})

(def routes #{["/api/task" :get (conj common-interceptors ( db-select-entity Task :db-select-task-interceptor ) list-interceptor) :route-name :task-get]
              ["/api/task" :post (conj common-interceptors db-insert-interceptor create-task-interceptor) :route-name :task-create]})
