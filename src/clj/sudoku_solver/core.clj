(ns sudoku-solver.core)

;; base02 - an incomplete sudoku puzzleboard
(def puzzle
  [[6 0 0 0 0 0 1 5 0]
   [9 5 4 7 1 0 0 8 0]
   [0 0 0 5 0 2 6 0 0]
   [8 0 0 0 9 4 0 0 6]
   [0 0 3 8 0 5 4 0 0]
   [4 0 0 3 7 0 0 0 8]
   [0 0 6 9 0 3 0 0 0]
   [0 2 0 0 4 7 8 9 3]
   [0 4 9 0 0 0 0 0 5]])

(defn diff [list1 list2]
  (mapcat
   (fn [[x n]] (repeat n x))
   (apply merge-with - (map frequencies [list1 list2]))))

(defn columns [board]
  (apply map vector board))

(defn missing [numlist]
  "Returns an array of numbers missing from the argument array"
  (remove zero? (diff (flatten numlist) (range 1 10))))

(defn take-at-point [point n col]
  (take n (last (split-at point col))))

(defn extract-grid [x y board]
  "Returns a 3x3 grid from a complete sudoku puzzleboard"
  (for [x (range x (+ x 3))
        y (range y (+ y 3))]
    (get-in board [x y])))

(defn grid-coord
  "Returns starting grid coordinate given a position"
  [n]
  (* (quot n 3) 3))

(quot 7 3)

(grid-coord 6)

(defn valid [x y board]
  "Returns a list of valid digits for the position x y"
  (map #(println %)
       [(nth (columns board) x)
        (nth board y)
        (extract-grid (mod (grid-coord x) 3) (mod (grid-coord y) 3) board)]))

(valid 0 2 puzzle)

(defn solve [board]
  "Organic or 'indirect' solving method programming ad-hoc ;)"
  ;; First, find any easy pickings - columns with a single missing digit.
  (let []))

(defn -main [& args]
  (println "AHOY!"))

;; ----------------------------------------------------------------------

;; solving position 0,4 (and returning updated puzzle board)
(missing (extract-grid 4 5 puzzle))
(extract-grid 4 5 puzzle)

(missing (extract-column 4 puzzle))
(missing (extract-row 0 puzzle))

puzzle
(columns puzzle)


(map missing (columns puzzle)) ;; missing numbers in each column
(map missing puzzle) ;; missing numbers in each row
(map missing (for [x (range 0 9 3)
                   y (range 0 9 3)]
               (extract-grid x y puzzle))) ;; missing numbers in each grid

(missing [[7 1 3 2 5] [7 4 3 2 9 8] [7 1 3 2 8]]) ;; results for point 0 0 only

for each grid
  - take the 3 columns that compose it
  - take the 3 rows that compose it

(for [x (range 0 9 3)
      y (range 0 9 3)]
  (extract-grid x y puzzle)) ;; all gris
