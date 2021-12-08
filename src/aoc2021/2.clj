(ns aoc2021.2)

(require '[clojure.string :as str])
(require '[clojure.core.match :refer [match]])
(require '[clojure.java.io :as io])

(def input
  (str/split
   (slurp (io/resource "2/input")) #"\n"))

(defn calc_position [[x y] line]
  (let [spline (str/split line #"\s")]
    (match [(nth spline 0) (Integer/parseInt (nth spline 1))]
      ["forward" i] [(+ x i) y]
      ["up" i] [x (- y i)]
      ["down" i] [x (+ y i)])))

(defn solve_part1 []
  (reduce *
          (reduce calc_position [0 0] input)))

(println "Day 2 #1: " (solve_part1))

; part 2

(defn calc_position_with_aim [[x y aim] line]
  (let [spline (str/split line #"\s")]
    (match [(nth spline 0) (Integer/parseInt (nth spline 1))]
      ["forward" i] [(+ x i) (+ y (* aim i)) aim]
      ["up" i] [x y (- aim i)]
      ["down" i] [x y (+ aim i)])))

(comment
  ; example from the site
  (->
   (calc_position_with_aim [0 0 0] "forward 5")
   (calc_position_with_aim "down 5")
   (calc_position_with_aim "forward 8")
   (calc_position_with_aim "up 3")
   (calc_position_with_aim "down 8")
   (calc_position_with_aim "forward 2")))

(defn solve_part2 []
  ; multiply x and y to get result
  (reduce * (take 2 (reduce calc_position_with_aim [0 0 0] input))))

(println "Day 2 #2: " (solve_part2))
