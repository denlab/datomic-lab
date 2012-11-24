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

(defn count-communities
  []
  (-> '[:find ?c
        :where
        [?c :community/name]]
      (d/q (d/db conn))))

(defn list-all-communities-name
  []
  (let [db (d/db conn)]
    (-> '[:find ?c
          :where
          [?c :community/name]]
        (d/q db)
        (->> (map (fn [[id]] (:community/name (d/entity db id))))))))

(defn list-com-name-url
  []
  (-> '[:find ?n ?u
        :where
        [?c :community/name ?n]
        [?c :community/url  ?u]]
      (d/q (d/db conn))))
