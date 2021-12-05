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
(defn most-common-bits [input]
  (map {false 0 true 1}
       (map >=
            (count-bits input)
            (repeat (count (first input)) (/ (count input) 2)))))

; inverse of most-common-bits
(defn least-common-bits [gamma] 
  (map - (repeat (count gamma) 1) gamma))

; convert bits-seq to 2r representation and eval
(defn conv [bits-seq] 
  (read-string (apply str "2r" bits-seq)))

(conv (most-common-bits example-input)) ; 22
(conv (least-common-bits (most-common-bits example-input))) ; 9

(def input
  (str/split
   (slurp (io/resource "3/input")) #"\n"))

(defn solve_part1 []
  (* (conv (most-common-bits input))
     (conv (least-common-bits (most-common-bits input)))))

(println "Day 3 #1: " (solve_part1))

; part 2

(defn parse-input [input]
  (map parse input))

(defn filter-nth-bit
  [input pos val] 
  (filter
   (fn [x]
     (= (nth x pos) val))
   input))

(comment
  ; example from the site
  (def mcb (most-common-bits example-input))
  (->
   (filter-nth-bit (parse-input example-input) 0 (nth mcb 0))
   (filter-nth-bit 1 (nth mcb 1))
   (filter-nth-bit 2 (nth mcb 2))
   (filter-nth-bit 3 (nth mcb 3))
   (filter-nth-bit 4 (nth mcb 4)))
  )

(defn with-indexes [seq] (map #(vector %1 %2) (range) seq))

(defn bitmask-filter [input mask] 
  (reduce
   (fn [coll [pos val]]
     (prn coll)
     (filter-nth-bit coll pos val)) 
   input
   (with-indexes mask)))

(bitmask-filter (parse-input example-input) mcb)