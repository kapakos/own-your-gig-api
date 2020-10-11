(ns own-your-gig-api.handler.release
  (:require [own-your-gig-api.schemas.release :refer [ReleaseRequestSchema]]
            [own-your-gig-api.models.release :refer [Release]]
            [ring.util.response :as ring-resp] 
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [toucan.db :as db]
            [java-time :as time]))

(def common-interceptors [(body-params/body-params) http/json-body])

; (defn- uid->created
;   [uid]
;   (created (str "/api" "/release/") {:release_uid uid}))

(defn- timestamp->offset-date-time
  [timestamp]
  (let [format (time/formatter :iso-offset-date-time)]
    (time/offset-date-time format timestamp)))

; (defn create-release-handler
;   [create-release-req]
;   (->> (update create-release-req :release_date timestamp->offset-date-time)
;        (db/insert! Release)
;        :release_uid
;        (uid->created)))

(defn get-release-handler
  [request]
  (->> (db/select Release)
       ring-resp/response))

(def routes #{["/release" :get (conj common-interceptors `get-release-handler)]})
; (def routes
;   (context "/release" []
;     :tags ["release"]
;     (POST "/" []
;       :body [create-release-req ReleaseRequestSchema]
;       (create-release-handler create-release-req))
;     (GET "/" []
;          (get-release-handler))))

