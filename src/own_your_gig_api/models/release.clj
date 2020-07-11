(ns own-your-gig-api.models.release
  (:require [toucan.models :refer [defmodel IModel]]))

(defmodel Release :release
  IModel
  (types [_]
         { :status :release_status})
  (primary-key [_] :release_uid))
