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
(defn increased? [input]
  (map
   (fn [a b]
     (if (> a b) 0 1))
   input
   (rest input)))
 
; reduce the ones in the result with + 
(defn solve_day1 []
  (reduce + (increased? input)))

; part 2

; add 3 consecutive elements 
(def sums (map (fn [a b c] 
  (+ a b c))
  input
  (rest input)
  (rest (rest input))))

; compare them to each other
(defn solve_day1_pt2 []
  (reduce + (increased? sums)))

(defn run [] 
  (println "Day 1 #1: " (solve_day1))
  (println "Day 1 #2: " (solve_day1_pt2)))

(run)