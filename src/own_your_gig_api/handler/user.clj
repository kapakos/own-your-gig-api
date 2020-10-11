(defn get-users-handler
  []
  (->> (db/select User)
       (map #(dissoc % :password_hash))
       ok))




