(ns datomic-lab.tutorial
  [:require
   [datomic.api     :as d]])


(def uri "datomic:mem://seattle")

(d/create-database uri)

(def conn (d/connect uri))

(def schema-tx (read-string (slurp "resources/seattle-schema.dtm")))
