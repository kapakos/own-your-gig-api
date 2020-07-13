(ns own-your-gig-api.handler.story
  (:require [compojure.api.sweet :refer [POST GET]]
            [own-your-gig-api.schemas.story :refer [StoryRequestSchema]]
            [own-your-gig-api.models.story :refer [Story]]
            [compojure.api.sweet :refer [context]]
            [toucan.db :as db]
            [ring.util.http-response :refer [created ok]]))

(defn- uid->created
  [uid]
  (created (str "/api" "/story/") {:story_uid uid}))

(defn create-story-handler
  [request]
  (->> (db/insert! Story request)
       :story_uid
       (uid->created)))

(defn get-stories-handler
  []
  (->> (db/select Story)
       ok))

(def routes
  (context "/story" []
    :tags ["story"]
    (POST "/" []
      :body [create-story-req StoryRequestSchema]
      (create-story-handler create-story-req))
    (GET "/" []
         (get-stories-handler))))
