(ns own-your-gig-api.handler.release
  (:require [compojure.api.sweet :refer [POST GET]]
            [own-your-gig-api.schemas.release :refer [ReleaseRequestSchema]]
            [own-your-gig-api.models.release :refer [Release]]
            [compojure.api.sweet :refer [context]]
            [toucan.db :as db]
            [java-time :as time]
            [ring.util.http-response :refer [ok created]]))

(defn- uid->created
  [uid]
  (created (str "/api" "/release/") {:release_uid uid}))

(defn- timestamp->offset-date-time
  [timestamp]
  (let [format (time/formatter :iso-offset-date-time)]
    (time/offset-date-time format timestamp)))

(defn create-release-handler
  [create-release-req]
  (->> (update create-release-req :release_date timestamp->offset-date-time)
       (db/insert! Release)
       :release_uid
       (uid->created)))

(defn get-release-handler
  []
  (->> (db/select Release)
       ok))

(def routes
  (context "/release" []
    :tags ["release"]
    (POST "/" []
      :body [create-release-req ReleaseRequestSchema]
      (create-release-handler create-release-req))
    (GET "/" []
         (get-release-handler))))
