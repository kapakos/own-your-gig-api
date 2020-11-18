(ns own-your-gig-api.handler.story
  (:require [own-your-gig-api.schemas.story :refer [StoryRequestSchema]]
            [own-your-gig-api.models.story :refer [Story]]
            [own-your-gig-api.handler.db_helper :refer [db-select-entity]]
            [own-your-gig-api.handler.interceptors :refer [common-interceptors]]
            [ring.util.response :as ring-resp] 
            [toucan.db :as db]
            [java-time :as time]))

(defn- uid->created
  [uid]
  (ring-resp/created (str "/api" "/story/") {:story_uid uid}))

(def db-insert-interceptor
  {:name :story-db-interceptor
   :leave
   (fn [context]
     (if-let [story (:result context)]
            (->>
              (db/insert! Story story)
              :story_uid
              (uid->created)
              (assoc context :response))
       context))})

(def create-story-interceptor
  {:name :create-story-interceptor
   :enter
   (fn [context]
     (let [story (get-in context [:request :json-params])]
       (assoc context :result story)))})

(def list-story-interceptor
  {:name :list-story-interceptor
   :leave
   (fn [context]
     (if-let [story-data (:result context)]
         (assoc context :response (ring-resp/response story-data))
         context))})

(def routes #{["/api/story" :get (conj common-interceptors ( db-select-entity Story :db-select-story-interceptor ) list-story-interceptor) :route-name :story-get]
              ["/api/story" :post (conj common-interceptors db-insert-interceptor create-story-interceptor) :route-name :story-post]})
