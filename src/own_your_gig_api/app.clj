(ns own-your-gig-api.app
  (:require [schema.core :as s]
            [own-your-gig-api.handler.user :as user-handler]
            [own-your-gig-api.handler.release :as release-handler]
            [own-your-gig-api.handler.story :as story-handler]
            [compojure.api.sweet :refer [api context]]))

(def app
  (api
   (context "/api" []
     :coercion :schema
     user-handler/routes
     release-handler/routes
     story-handler/routes)))
