(ns datomic-lab.tutorial
  [:use
   [clojure.pprint :only [pp pprint print-table]]]
  [:require
   [datomic.api     :as d]])


(def uri "datomic:mem://seattle")

(d/create-database uri)

(def conn (d/connect uri))

(def base-dir "doc/datomic-0.8.3599-samples/seattle/")

(def schema-tx (read-string (slurp (str base-dir "seattle-schema.dtm"))))

@(d/transact conn schema-tx)

(def data-tx (read-string (slurp (str base-dir "seattle-data0.dtm"))))

@(d/transact conn data-tx)

(defn query
  "Run the query q against the db"
  ([q   ] (d/q q (d/db conn)))
  ([q db] (d/q q db)))

(defn count-communities
  []
  (query '[:find ?c
           :where
           [?c :community/name]]))

(defn list-all-communities-name
  []
  (let [db (d/db conn)]
    (-> '[:find ?c
          :where
          [?c :community/name]]
        (query db)
        (->> (map (fn [[id]] (:community/name (d/entity db id))))))))

(defn list-com-name-url
  []
  (query '[:find ?n ?u
           :where
           [?c :community/name ?n]
           [?c :community/url  ?u]]))
