(ns own-your-gig-api.handler.interceptors
  (:require [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http :as http]
            [io.pedestal.interceptor :as interceptor]
            [buddy.auth.middleware :as auth.middleware]
            [buddy.auth.backends :as auth.backends]
            [own-your-gig-api.auth.core :refer [token-auth-backend]]))

(def authentication-interceptor
  (interceptor/interceptor
    {:name ::authenticate
     :enter 
      (fn [context]
        (update context :request auth.middleware/authentication-request token-auth-backend))}))

(def token-interceptor
  (interceptor/interceptor
    {:name :token-interceptor
     :enter
      (fn [context]
        (let [ident (get-in context [:request :identity])]
          (assoc context :response ident)))}))

(def common-auth-interceptors [(body-params/body-params) 
                               http/json-body
                               authentication-interceptor
                               token-interceptor])

(def common-interceptors [(body-params/body-params) 
                          http/json-body])

