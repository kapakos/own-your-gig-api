(ns own-your-gig-api.schemas.release
  (:refer-clojure :exclude [format])
  (:require [schema.core :as s]
            [own-your-gig-api.utils.string_util :as str]
            [java-time :as time]))

(defn valid-version?
  [name]
  (str/max-length? 30 name))

(defn status-is-one-of?
  [status]
  (.contains ["Not Released" "Released"] status))

(defn valid-date?
  [date]
  (try
    (let [format (time/formatter :iso-offset-date-time)]
      (time/offset-date-time format date)
      true)
    (catch Exception e false)))

(s/defschema ReleaseRequestSchema
  {:version (s/constrained s/Str valid-version?)
   :status (s/constrained s/Str status-is-one-of?)
   :release_date (s/constrained s/Str valid-date?)})

