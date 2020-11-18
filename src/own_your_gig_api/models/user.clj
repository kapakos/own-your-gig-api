(ns own-your-gig-api.models.user
  (:require [toucan.models :refer [defmodel IModel]]
            [toucan.db :as db]
            [buddy.hashers :as hashers]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;; MODEL
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmodel User :user
  IModel
  (types [_]
         {:userrole :role})
  (primary-key [_] :user_uid))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;; HELPER
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def hashing-alg {:alg :bcrypt+sha512})

(defn pwd-derive
  [secret]
  (hashers/derive secret hashing-alg))

(defn- dissoc-password
  [user]
  (dissoc user :password_hash))

(defn get-user-by-creds
  [email secret]
  (println (str "Email: " email " - secret: " secret))
  (if-let [user (db/select-one User :email email)]
    (do
      (println (str "User: " user))
      (if-let [derived-secret (:password_hash user)]
        (if (hashers/verify secret derived-secret)
          (dissoc-password user))))))

(defn get-user-by-id
  [id]
  (->> (db/select-one User :user_id id)
       (dissoc-password)))
