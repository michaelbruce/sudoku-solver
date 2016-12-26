(ns sudoku-solver.core
  (:require [clojure.string :refer [index-of]]))

;; base02 - an incomplete sudoku puzzleboard
(def puzzle
  [6 0 0 0 0 0 1 5 0
   9 5 4 7 1 0 0 8 0
   0 0 0 5 0 2 6 0 0
   8 0 0 0 9 4 0 0 6
   0 0 3 8 0 5 4 0 0
   4 0 0 3 7 0 0 0 8
   0 0 6 9 0 3 0 0 0
   0 2 0 0 4 7 8 9 3
   0 4 9 0 0 0 0 0 5])

(defn diff [list1 list2]
  (mapcat
   (fn [[x n]] (repeat n x))
   (apply merge-with - (map frequencies [list1 list2]))))

(defn rows [board]
  (->> board (partition 9) (map vec) (into [])))

(defn columns [board]
  (apply mapv vector (rows board)))

(columns puzzle)

(defn missing [numlist]
  "Returns an array of numbers missing from the argument array"
  (remove zero? (diff (flatten numlist) (range 1 10))))

(defn extract-grid [x y board]
  "Returns a 3x3 grid from a complete sudoku puzzleboard"
  (for [x (range x (+ x 3))
        y (range y (+ y 3))]
    (get-in (rows board) [x y])))

(defn value-at-position [x y board]
  (-> (rows board) (nth y) (nth x)))

(defn include? [value col]
  (not (nil? (some #{value} col))))

(defn value-present [value grid-x grid-y puzzle]
  "Returns whether a value exists in each of the rows and columns that make a grid"
  (let [rows (subvec (rows puzzle) grid-x (+ grid-x 3))
        columns (subvec (columns puzzle) grid-y (+ grid-y 3))]
    (concat (map #(include? 6 %) rows)
            (map #(include? 6 %) columns))))

(value-present 6 0 3 puzzle)

;; Solving sudoku
;; => take a puzzle as a vector
;;   => take the top middle grid
;;   => determine where 6 should be placed
;;     => look at the top row, it contains 6 so it can't go there
;;     => middle row doesn't have 6 - mark row 1
;;     => bottom row has a 6 so it can't go there
;;     => all columns are marked because they contain 6
;; use the marked/unmarked results from the rows/columns to determine potential
;; places to have 6
;;  => for each row, is col position marked? is position 0/empty?
;; [{:x 3 :y 2} {:x 3 :y 1}]
;; (assoc puzzle (+ x (* y 9)) 6)

;; => will keep going until (indexOf puzzle 0) is -1

;; (defn exists?)

;; XXX get coord value
;; XXX replace coord value

(defn missing-positions [dataset]
  (into [] (remove nil? (mapv #(if (= false (second %))(first %))
                              (map-indexed vector dataset)))))

(defn solve [puzzle]
  (let [grows (subvec (rows puzzle) 0 3)
        gcols (subvec (columns puzzle) 3 6)]
    (let [mrows (missing-positions (mapv #(include? 6 %) grows))
          mcols (missing-positions (mapv #(include? 6 %) gcols))]
      (let [missing-coords (for [x mrows y mcols] (vector x y))]
        missing-coords))))

(solve puzzle)
(frequencies [true false false])

;; iterate through coords and find 0
(for [x [1] y [0 1 2]] (vector x y))

(def test-vec [true false false])
(missing-positions test-vec)

;; if only one row OR col doesn't have 

;; XXX working out
;; (index-of (solve puzzle) true)
;;(mapv #(index-of % 6) (subvec (rows puzzle) 0 3))
;;(.indexOf (first (subvec (rows puzzle) 0 3)) 6)

(defn -main [& args]
  (println "AHOY!"))
