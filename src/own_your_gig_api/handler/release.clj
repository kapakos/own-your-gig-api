(ns own-your-gig-api.handler.release
  (:require [own-your-gig-api.schemas.release :refer [ReleaseRequestSchema]]
            [own-your-gig-api.models.release :refer [Release]]
            [own-your-gig-api.handler.helper :refer [common-interceptors]]
            [ring.util.response :as ring-resp] 
            [toucan.db :as db]
            [java-time :as time]))

(defn- uid->created
  [uid]
  (ring-resp/created (str "/api" "/release/") {:release_uid uid}))

(defn- timestamp->offset-date-time
  [timestamp]
  (let [format (time/formatter :iso-offset-date-time)]
    (time/offset-date-time format timestamp)))

(def db-insert-interceptor
  {:name :release-db-interceptor
   :leave
   (fn [context]
     (if-let [release (:result context)]
            (->>
              (db/insert! Release release)
              :release_uid
              (uid->created)
              (assoc context :response))
       context))})

(def create-release-interceptor
  {:name :create-release-interceptor
   :enter
   (fn [context]
     (let [release (get-in context [:request :json-params])]
       (assoc context
              :result (update release :release_date timestamp->offset-date-time))))})

(def db-select-interceptor
 {:name :db-get-release-interceptor
  :enter 
  (fn [context]
   (assoc context :result (db/select Release)))})   

(def list-release-interceptor
  {:name :list-release-interceptor
   :leave
   (fn [context]
     (if-let [release-data (:result context)]
         (assoc context :response (ring-resp/response release-data))
         context))})

(def routes #{["/api/release" :get (conj common-interceptors db-select-interceptor list-release-interceptor) :route-name :release-get]
              ["/api/release" :post (conj common-interceptors db-insert-interceptor create-release-interceptor) :route-name :release-post]})
