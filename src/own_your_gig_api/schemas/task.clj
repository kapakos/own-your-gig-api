(ns own-your-gig-api.schemas.task
  (:require [schema.core :as s]
            [own-your-gig-api.utils.string_util :as str]))

(defn valid-title?
  [name]
  (str/max-length? 100 name))

(defn valid-uid?
  [uid]
  (str/max-length? 200 uid))

(defn valid-desc?
  [name]
  (str/max-length? 500 name))

(defn status-is-one-of?
  [status]
  (.contains ["Not Started" "In Progress" "Blocked" "Done"] status))

(s/defschema TaskRequestSchema
  {:title (s/constrained s/Str valid-title?)
   :description (s/constrained s/Str valid-desc?)
   :task_status (s/constrained s/Str status-is-one-of?)
   :story_uid (s/constrained s/Str str/is-uuid?)})
