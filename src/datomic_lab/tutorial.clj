(ns datomic-lab.tutorial
  [:use
   [clojure.pprint :only [pp pprint print-table]]]
  [:require
   [datomic.api     :as d]])


(def uri "datomic:mem://seattle")

(d/create-database uri)

(def conn (d/connect uri))

(def schema-tx (read-string (slurp "resources/seattle-schema.dtm")))

@(d/transact conn schema-tx)
