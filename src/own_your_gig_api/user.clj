(ns own-your-gig-api.user
  (:require [schema.core :as s]
            [own-your-gig-api.models.user :refer [User]]
            [own-your-gig-api.schemas.user :refer [UserRequestSchema]]
            [buddy.hashers :as hashers]
            [clojure.set :refer [rename-keys]]
            [toucan.db :as db]
            [ring.util.http-response :refer [created ok]]
            [compojure.api.sweet :refer [POST GET api context]]))

(defn uid->created
  [uid]
  (created (str   "/api" "/users/") {:user_uid uid}))

(defn canonicalize-user-req
  [user-req]
  (-> (update user-req :password hashers/derive)
      (rename-keys {:password  :password_hash})))

(defn create-user-handler
  [create-user-req]
  (->> (canonicalize-user-req create-user-req)
       (db/insert! User)
       :user_uid
       uid->created))

(defn create-release-handler
  [create-release-req]
  (->> (db/insert! Release)
       :release_uid
       (created (str "/api" "/request/") {:release_uid uid}))

(defn get-users-handler
  []
  (->> (db/select User)
       (map #(dissoc % :password_hash))
       ok))

(def app
  (api
    (context "/api" []
             :coercion :schema
             (POST "/user" []
                   :body [create-user-req UserRequestSchema]
                   (create-user-handler create-user-req))
             (GET "/user" []
                  (get-users-handler))
             POST "/release" []
                    :body [create-release-req ReleaseRequestSchema]
                    (create-release-handler create-release-req))))
