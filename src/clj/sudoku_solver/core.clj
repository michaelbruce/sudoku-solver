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

(defn grid-by-x-y [x y]
  "Returns the grid that the position x y belongs to")

(defn take-at-point [point n col]
  (take n (last (split-at point col))))

(defn extract-grid [position board]
  "Returns a 3x3 grid from a complete sudoku puzzleboard"
  (dotimes [n 3]
    (take-at-point 0 3 (get board n))))

(defn extract-column [position board]
  (map #(nth % position) board))

(defn extract-row [position board]
  (nth board position))

(defn valid [x y board]
  "Returns a list of valid digits for the position x y"
  (missing (extract-column y board))
  (missing (extract-row y board))
  )

(defn solve [board]
  "Organic or 'indirect' solving method programming ad-hoc ;)"
  ;; First, find any easy pickings - columns with a single missing digit.
  (dotimes [n (count board)]
    (if (= 1 (count (missing (extract-column n board))))
      (println "Found column with exactly one missing digit"))
    (if (= 1 (count (missing (extract-row n board))))
      (println "Found row with exactly one missing digit"))))

(defn -main [& args]
  (println "AHOY!"))
