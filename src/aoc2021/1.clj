(ns aoc2021.1)

(require '[clojure.string :as str])
(require '[clojure.java.io :as io])

; parse input file to collection of ints
(def input
  (map #(Integer/parseInt %)
    (str/split
      (slurp (io/resource "1/input")) #"\n")
  )
)

; map and compare each element with the next, return 1 if value has increased 
(def increases (map
 (fn [a b]
   (if (> a b) 0 1))
 input
 (rest input)))
 
; reduce the ones in the result with + 
(reduce + increases)