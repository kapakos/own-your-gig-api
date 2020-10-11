(ns own-your-gig-api.models.task
  (:require [toucan.models :refer [defmodel IModel]]))

(defmodel Task :task
  IModel
  (types [_]
         {:task_status :task_status})
  (primary-key [_] :task_uid))

