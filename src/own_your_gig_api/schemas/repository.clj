(ns own-your-gig-api.schemas.repository
  (:require [schema.core :as s]
            [own-your-gig-api.utils.string_util :as str]))

(defn valid-name?
  [title]
  (str/non-blank-with-max-length? 200 title))

(defn valid-version?
  [version]
  (str/max-length? 20 version))

(defn valid-url?
  [url]
  (str/max-length? 200 url))

(s/defschema RepositoryRequestSchema
  {:name (s/constrained s/Str valid-name?)
   :url (s/constrained s/Str valid-url?)
   :version (s/constrained s/Str valid-version?)
   :story_uid (s/constrained s/Str str/is-uuid?)})
