(ns sudoku-solver.core)

;; base01 - solve a 3x3 grid box
(def threegrid
  [[1 2 3]
   [4 5 6]
   [7 0 9]])

(defn diff [list1 list2]
  (mapcat
   (fn [[x n]] (repeat n x))
   (apply merge-with - (map frequencies [list1 list2]))))

(defn missing-list [numlist]
  "Returns an array of numbers missing from the argument array"
  (remove zero? (diff (flatten numlist) (range 1 10))))

(defn missing-in-square [position]
  "Takes a 3x3 grid from the sudoku map and returns a list of missing numbers"
  ;; TODO continue here
  (+ 5 8))

(defn -main [& args]
  (println "AHOY!"))
