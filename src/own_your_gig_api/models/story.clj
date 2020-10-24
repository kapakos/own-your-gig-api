(ns own-your-gig-api.models.story
  (:require [toucan.models :refer [defmodel IModel]]))

(defmodel Story :story
  IModel
  (types [_]
         {:story_status :story_status
          :pr_status :pr_status})
  (primary-key [_] :story_uid))

