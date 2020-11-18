(ns own-your-gig-api.handler.auth
  "Api authentication using basic auth"
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.interceptor.chain :as interceptor.chain]
            [io.pedestal.interceptor.error :refer [error-dispatch]]
            [ring.util.response :as ring-resp]
            [own-your-gig-api.handler.interceptors :refer [common-auth-interceptors]]
            [buddy.auth :as auth]))

(def tokens {:2f904e245c1f5 :user})

; (defn create-random-token
;   []
;   (let [randomdata (nonce/random-bytes 16)]
;     (codecs/bytes->hex randomdata)))

(def routes #{["/api/auth" :post (conj common-auth-interceptors) :route-name :auth-post]})  
               
