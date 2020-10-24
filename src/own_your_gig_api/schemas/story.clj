(ns own-your-gig-api.schemas.story
  (:require [schema.core :as s]
            [own-your-gig-api.utils.string_util :as str]))

(defn valid-title?
  [title]
  (str/non-blank-with-max-length? 200 title))

(defn valid-description?
  [desc]
  (str/max-length? 500 desc))

(defn valid-url?
  [url]
  (str/max-length? 200 url))

(defn valid-story-status?
  [status]
  (.contains ["Not Started" "In Progress" "QA" "In Review" "Blocked" "Completed"] status))

(defn valid-pr-status?
  [status]
  (.contains ["NA" "Open" "In Conflict" "In Review" "Declined" "Merged"] status))

(s/defschema StoryRequestSchema
  {:title (s/constrained s/Str valid-title?)
   :description (s/constrained s/Str valid-description?)
   :story_ticket_url (s/constrained s/Str valid-url?)
   :story_status (s/constrained s/Str valid-story-status?)
   :pr_status (s/constrained s/Str valid-pr-status?)
   :design_url (s/constrained s/Str valid-url?)
   :documentation_url (s/constrained s/Str valid-url?)})
