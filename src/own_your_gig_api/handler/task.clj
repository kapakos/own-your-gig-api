(ns own-your-gig-api.handler.task
  (:require [own-your-gig-api.schemas.task :refer [TaskRequestSchema]]
            [own-your-gig-api.models.task :refer [Task]]
            [own-your-gig-api.handler.helper :refer [common-interceptors]]
            [ring.util.response :as ring-resp] 
            [toucan.db :as db]))

(defn- uid->created
  [uid]
  (ring-resp/created (str "/api" "/task/") {:task_uid uid}))

(def db-select-interceptor
 {:name :db-get-release-interceptor
  :enter 
  (fn [context]
   (assoc context :result (db/select Task)))})   

(def list-interceptor
  {:name :list-release-interceptor
   :leave
   (fn [context]
     (if-let [result (:result context)]
         (assoc context :response (ring-resp/response result))
         context))})

(def db-insert-interceptor
  {:name :db-insert-interceptor})
   
(def db-insert-interceptor
  {:name :db-insert-interceptor
   :leave
   (fn [context]
     (if-let [task(:result context)]
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
       (assoc context :result task)))})

(def routes #{["/api/task" :get (conj common-interceptors db-select-interceptor list-interceptor) :route-name :task-get]
              ["/api/task" :post (conj common-interceptors db-insert-interceptor create-task-interceptor) :route-name :task-create]})
