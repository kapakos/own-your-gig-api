(ns own-your-gig-api.schemas.story
  (:require [schema.core :as s]
            [own-your-gig-api.utils.string_util :as str]))

(defn valid-title?
  [name]
  (str/max-length? 200 name))

(defn valid-url?
  [url]
  (str/max-length? 200 url))

(defn valid-desc?
  [name]
  (str/max-length? 500 name))

(defn status-is-one-of?
  [status]
  (.contains ["Not Started" "In Progress" "QA" "In Review" "Blocked" "Completed"] status))

(defn pr-status-is-one-of?
  [status]
  (.contains ["NA" "Open" "In Conflict" "In Review" "Declined" "Merged"] status))

(s/defschema StoryRequestSchema
  {:title (s/constrained s/Str valid-title?)
   :description (s/constrained s/Str valid-desc?)
   :story_ticket_url (s/constrained s/Str valid-url?)
   :design_url (s/constrained s/Str valid-url?)
   :documentation_url (s/constrained s/Str valid-url?)
   :story_status (s/constrained s/Str status-is-one-of?)
   :pr_status (s/constrained s/Str pr-status-is-one-of?)})
