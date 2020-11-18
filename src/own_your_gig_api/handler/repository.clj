(ns own-your-gig-api.handler.repository
  (:require [own-your-gig-api.schemas.repository :refer [RepositoryRequestSchema]]
            [own-your-gig-api.models.repository :refer [Repository]]
            [own-your-gig-api.utils.string_util :as str] 
            [own-your-gig-api.handler.db_helper :refer [db-select-entity]]
            [own-your-gig-api.handler.interceptors :refer [common-interceptors]]
            [ring.util.response :as ring-resp] 
            [toucan.db :as db]))

(defn- uid->created
  [uid]
  (ring-resp/created (str "/api" "/repository/") {:repository_uid uid}))

(def db-select-interceptor
 {:name :db-get-repository-interceptor
  :enter 
  (fn [context]
   (assoc context :result (db/select Repository)))})   

(def list-interceptor
  {:name :list-repository-interceptor
   :leave
   (fn [context]
     (if-let [result (:result context)]
         (assoc context :response (ring-resp/response result))
       context))})

(def db-insert-interceptor
  {:name :db-insert-interceptor
   :leave
   (fn [context]
     (if-let [repository (:result context)]
       (->>
          (db/insert! Repository repository)
          :repository_uid
          (uid->created)
          (assoc context :response))
       context))})

(def create-repository-interceptor
  {:name :create-repository-interceptor
   :enter
   (fn [context]
     (let [repository (get-in context [:request :json-params])]
       (->> 
         (update repository :story_uid str/str->uuid)
         (assoc context :result))))})

(def routes #{["/api/repository" :get (conj common-interceptors (db-select-entity Repository :db-select-repository-interceptor ) list-interceptor) :route-name :repository-get]
              ["/api/repository" :post (conj common-interceptors db-insert-interceptor create-repository-interceptor) :route-name :repository-create]})
