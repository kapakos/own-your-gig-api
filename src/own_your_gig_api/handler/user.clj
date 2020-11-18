(ns own-your-gig-api.handler.user
  (:require [own-your-gig-api.schemas.user :refer [UserRequestSchema]]
            [own-your-gig-api.models.user :refer [User pwd-derive]]
            [own-your-gig-api.handler.interceptors :refer [common-interceptors]]
            [clojure.set :refer [rename-keys]]
            [ring.util.response :as ring-resp] 
            [toucan.db :as db]))

(defn uid->created
  [uid]
  (ring-resp/created (str "/api" "/user/") {:user_uid uid}))

(defn canonicalize-user-req
  [user-req]
  (-> (update user-req :auth_secret pwd-derive)
      (rename-keys {:auth_secret :password_hash :auth_username :username :auth_email :email})))

(defn get-users-handler
  [request]
  (->> (db/select User)
       (map #(dissoc % :password_hash))
       ring-resp/response))

(def create-user-interceptor
 {:name :create-user-interceptor
  :enter 
  (fn [context]
    (let [user (get-in context [:request :form-params])]
      (assoc context :result (canonicalize-user-req user))))})

(def db-create-interceptor
  {:name :db-create-interceptor
   :leave
   (fn [context]
     (if-let [user (:result context)]
      (->>
        (db/insert! User user)
        :user_uid
        ( uid->created)
        (assoc context :response))
      context))})

(def routes #{["/api/user" :get (conj common-interceptors `get-users-handler) :route-name :user-get]
              ["/api/user" :post (conj common-interceptors db-create-interceptor create-user-interceptor) :route-name :user-post]})
