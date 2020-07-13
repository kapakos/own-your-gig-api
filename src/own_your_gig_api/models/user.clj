(ns own-your-gig-api.models.user
  (:require [toucan.models :refer [defmodel IModel]]))

(defmodel User :user
  IModel
  (types [_]
         {:userrole :role})
  (primary-key [_] :user_uid))
