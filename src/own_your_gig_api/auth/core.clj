(ns own-your-gig-api.auth.core
  (:require [own-your-gig-api.models.user :as user-model]
            [buddy.auth.backends :as auth.backends]))

(defn auth-by-creds-fn
  [request authdata]
  (println (str "Authdata: " authdata))
  (println (str "Request: " request))
  (let [{:keys [auth_email auth_secret]} authdata]
    (if-let [known_user (user-model/get-user-by-creds auth_email auth_secret)]
      (println (str "Known User: " known_user)))
    (println "Result: Didnt work")))

(def token-auth-backend
  (auth.backends/basic {:realm "OYGAPI"
                        :authfn auth-by-creds-fn}))
