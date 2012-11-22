(ns datomic-lab.core
  [:require [datomic.api :as d]])


(def uri "datomic:mem://hello")

(d/create-database uri)

(def conn (d/connect uri))

(def datom ["db/add"
            (d/tempid "db.part/user")
            "db/doc"
            "hello world"])

(def resp (d/transact conn [datom]))

(def db (d/db conn))

(defn q-hello-world
  []  (d/q [:find '?entity :where ['?entity :db/doc "hello world"]]
           db))
