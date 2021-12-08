(ns aoc2021.1)

(require '[clojure.string :as str])
(require '[clojure.java.io :as io])

; parse input file to collection of ints
(def input
  (map #(Integer/parseInt %)
       (str/split
        (slurp (io/resource "1/input")) #"\n")))

; map and compare each element with the next, return 1 if value has increased 
(defn increased? [input]
  (map
   (fn [a b]
     (if (> a b) 0 1))
   input
   (rest input)))

; reduce the ones in the result with + 
(defn solve_part1 []
  (reduce + (increased? input)))

; part 2

; add 3 consecutive elements 
(def sums (map (fn [a b c]
                 (+ a b c))
               input
               (rest input)
               (rest (rest input))))

; compare them to each other
(defn solve_part2 []
  (reduce + (increased? sums)))

(println "Day 1 #1: " (solve_part1))
(println "Day 1 #2: " (solve_part2))