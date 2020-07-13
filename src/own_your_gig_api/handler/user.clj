(ns own-your-gig-api.handler.user
  (:require [own-your-gig-api.schemas.user :refer [UserRequestSchema]]
            [own-your-gig-api.models.user :refer [User]]
            [buddy.hashers :as hashers]
            [clojure.set :refer [rename-keys]]
            [compojure.api.sweet :refer [POST GET]]
            [compojure.api.sweet :refer [context]]
            [ring.util.http-response :refer [created ok]]
            [toucan.db :as db]))

(defn uid->created
  [uid]
  (created (str "/api" "/users/") {:user_uid uid}))

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

(defn get-users-handler
  []
  (->> (db/select User)
       (map #(dissoc % :password_hash))
       ok))

(def routes
  (context "/user" []
    :tags ["user"]
    (POST "/" []
      :body [create-user-req UserRequestSchema]
      (create-user-handler create-user-req))
    (GET "/" []
      (get-users-handler))))
