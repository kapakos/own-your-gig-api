(ns own-your-gig-api.models.types
  (:require [toucan.models :as models])
  (:import [org.postgresql.util PGobject]))

(defn- pg-object-fn
  [pg-type]
  (fn [value]
    (doto (PGobject.)
      (.setType pg-type)
      (.setValue (name value)))))

(defn add-types
  []
  (models/add-type! :role
                    :in (pg-object-fn "role")
                    :out keyword)
  (models/add-type! :release_status
                    :in (pg-object-fn "release_status")
                    :out keyword)
  (models/add-type! :story_status
                    :in (pg-object-fn "story_status")
                    :out keyword)
  (models/add-type! :pr_status
                    :in (pg-object-fn "pr_status")
                    :out keyword))
