(ns aoc2021.3)

(require '[clojure.string :as str])
(require '[clojure.java.io :as io])

(def example-input
  ["00100" "11110" "10110" "10111"
   "10101" "01111" "00111" "11100"
   "10000" "11001" "00010" "01010"])

(defn parse [line]
  (map #(Character/getNumericValue %) line))

; count bits of each binary column
(defn count-bits [input]
  (reduce
   (fn [coll line]
     (map + coll (parse line)))
   (repeat (count (first input)) 0) ; initial coll vector
   input))

; 1 if bit-count at position x >= input size / 2 
(defn gamma-rate [input]
  (map {false 0 true 1}
       (map >=
            (count-bits input)
            (repeat (count (first input)) (/ (count input) 2)))))

; inverse of gamma-rate
(defn epsilon-rate [gamma] 
  (map - (repeat (count gamma) 1) gamma))

; convert bits-seq to 2r representation and eval
(defn conv [bits-seq] 
  (read-string (apply str "2r" bits-seq)))

(conv (gamma-rate example-input)) ; 22
(conv (epsilon-rate (gamma-rate example-input))) ; 9

(def input
  (str/split
   (slurp (io/resource "3/input")) #"\n"))

(defn solve_part1 []
  (* (conv (gamma-rate input))
     (conv (epsilon-rate (gamma-rate input)))))

(println "Day 3 #1: " (solve_part1))
