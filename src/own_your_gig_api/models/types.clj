(ns own-your-gig-api.models.types
  (:require [toucan.models :as models])
  (:import [org.postgresql.util PGobject]))

(defn- pg-object-fn
  [pg-type]
  (fn [value]
    (doto (PGobject.)
      (.setType pg-type)
      (.setValue (name value)))))

(def add-types 
  ((models/add-type! :role
                      :in (pg-object-fn "role")
                      :out keyword)
   (models/add-type! :release_status
                     :in (pg-object-fn "release_status")
                     :out keyword)))
