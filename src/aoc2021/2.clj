(ns aoc2021.2)

(require '[clojure.string :as str])
(require '[clojure.java.io :as io])
(require '[clojure.core.match :refer [match]])

(def input
  (str/split
   (slurp (io/resource "2/input")) #"\n"))

(defn reducefn [[x y] line]
  (let [spline (str/split line #"\s")]
    (match [(nth spline 0) (Integer/parseInt (nth spline 1))]
      ["forward" i] [(+ x i) y]
      ["up" i] [x (- y i)]
      ["down" i] [x (+ y i)])))

(defn solve_day2 []
  (reduce *
    (reduce reducefn [0 0] input)))

(println "Day 2 #1: " (solve_day2))

; part 2