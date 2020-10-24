(ns own-your-gig-api.schemas.user
  (:require [schema.core :as s]
            [own-your-gig-api.utils.string_util :as str]))

(defn valid-username?
  [name]
  (str/non-blank-with-max-length? 100 name))

(defn valid-password?
  [password]
  (str/length-in-range? 5 150 password))

(defn valid-name?
  [name]
  (str/max-length? 50 name))

(defn role-is-one-of?
  [userrole]
  (.contains ["Guest" "Developer" "Administrator" ""] userrole))

(s/defschema UserRequestSchema
  {:username  (s/constrained s/Str valid-username?)
   :password  (s/constrained s/Str valid-password?)
   :email (s/constrained s/Str str/email?)
   :firstname (s/constrained s/Str valid-name?)
   :lastname (s/constrained s/Str valid-name?)
   :userrole (s/constrained s/Str role-is-one-of?)
   })

