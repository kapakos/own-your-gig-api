(ns own-your-gig-api.handler.helper
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]))

(def common-interceptors [(body-params/body-params) http/json-body])
