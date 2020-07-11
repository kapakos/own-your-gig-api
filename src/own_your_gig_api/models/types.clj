(ns own-your-gig-api.models)

(defn add-types
  []
  (models/add-type! :role
                    :in (pg-object-fn "role")
                    :out keyword)
  (models/add-type! :release_status
                    :in (pg-object-fn "release_status")
                    :out keyword))
