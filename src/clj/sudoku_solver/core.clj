(ns sudoku-solver.core)

;; base01 - solve a 3x3 grid box
(def threegrid
  [[1 2 3]
   [4 5 6]
   [7 0 9]])

;; base02 - an incomplete sudoku puzzleboard
(def puzzle
  [[8 2 6 3 0 0 0 9 0]
   [5 0 0 2 0 7 0 0 6]
   [4 0 0 9 0 8 0 1 0]
   [6 0 0 0 8 0 0 4 0]
   [0 0 0 7 5 2 0 0 0]
   [0 8 0 0 3 0 0 0 2]
   [0 6 0 4 0 3 0 0 1]
   [1 0 0 5 0 6 0 0 8]
   [0 5 0 0 0 1 6 3 7]])

(defn diff [list1 list2]
  (mapcat
   (fn [[x n]] (repeat n x))
   (apply merge-with - (map frequencies [list1 list2]))))

(defn missing [numlist]
  "Returns an array of numbers missing from the argument array"
  (remove zero? (diff (flatten numlist) (range 1 10))))

(defn extract-grid [position board]
  "Returns a 3x3 grid from a complete sudoku puzzleboard"
  (+ 5 8))

(defn extract-column [position board]
  (map #(nth % pos) board))

(defn extract-row [position board]
  (nth board position))

;; TODO continue from here
(defn solve-row [board]
  (+ 5 8))

(defn -main [& args]
  (println "AHOY!"))
