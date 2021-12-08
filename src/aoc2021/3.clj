(ns aoc2021.3)

(require '[clojure.string :as str])
(require '[clojure.java.io :as io])

(defn parse-line [line]
  (map #(Character/getNumericValue %) line))

(defn parse-input [input]
  (map parse-line input))

(def example-input
  (parse-input ["00100" "11110" "10110" "10111"
                "10101" "01111" "00111" "11100"
                "10000" "11001" "00010" "01010"]))

; count bits of each binary column
(defn count-bits [input-seq]
  (reduce
   (fn [coll row]
     (map + coll row))
   (repeat (count (first input-seq)) 0) ; initial coll vector
   input-seq))

; 1 if bit-count at position x >= input size / 2 
(defn most-common-bits [input-seq]
  (map {false 0 true 1}
       (map >=
            (count-bits input-seq)
            (repeat (count (first input-seq)) (/ (count input-seq) 2)))))

(most-common-bits example-input)

; 1 if bit-count at position x >= input size / 2 
(defn least-common-bits [input-seq]
  (map -
       (repeat (count (first input-seq)) 1)
       (most-common-bits input-seq)))

(least-common-bits example-input)

; convert bits-seq to 2r representation and eval
(defn conv [bits-seq]
  (read-string (apply str "2r" bits-seq)))

(conv
 (most-common-bits example-input)) ; 22

(conv
 (least-common-bits example-input)) ; 9

(def input
  (parse-input
   (str/split
    (slurp (io/resource "3/input")) #"\n")))

(defn solve_part1 [input]
  (* (conv (most-common-bits input))
     (conv (least-common-bits input))))

(println "Day 3 #1: " (solve_part1 input))

; part 2

(defn filter-nth-bit
  [input pos val]
  (filter
   (fn [x]
     (= (nth x pos) val))
   input))

(defn bitmask-filter [input-seq bitmask-fn]
  (flatten
   (reduce
    (fn [coll pos]
      (if (> (count coll) 1)
        (let [val (nth (bitmask-fn coll) pos)]
          (filter-nth-bit coll pos val))
        coll))
    input-seq
    (range 0 (count (first input-seq))))))

(bitmask-filter example-input most-common-bits)

(bitmask-filter example-input least-common-bits)

(defn solve_part2 [input]
  (* (conv (bitmask-filter input most-common-bits))
     (conv (bitmask-filter input least-common-bits))))

(solve_part2 example-input)

(println "Day 3 #2: " (solve_part2 input))
