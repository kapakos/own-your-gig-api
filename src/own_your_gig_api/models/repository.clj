(ns own-your-gig-api.models.repository
  (:require [toucan.models :refer [defmodel IModel]]))

(defmodel Repository :repository
  IModel
  (primary-key [_] :repository_uid))

