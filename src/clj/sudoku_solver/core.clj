(ns sudoku-solver.core)

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
  (apply map vector (rows board)))

(defn missing [numlist]
  "Returns an array of numbers missing from the argument array"
  (remove zero? (diff (flatten numlist) (range 1 10))))

(defn take-at-point [point n col]
  (take n (last (split-at point col))))

(defn extract-grid [x y board]
  "Returns a 3x3 grid from a complete sudoku puzzleboard"
  (for [x (range x (+ x 3))
        y (range y (+ y 3))]
    (get-in (rows board) [x y])))

(defn grid-coord [position]
  (* (quot position 3) 3))

(defn position-info [x y board]
  [(nth (columns board) x)
   (nth (rows board) y)
   ;; extract-grid only works as expected with args reversed?
   (extract-grid (grid-coord y) (grid-coord x) (rows board))
   (value-at-position x y (rows board))])

(defn possible-values
  "Returns list of possible values for a given coordinate"
  [x y board]
  (missing
    (distinct
      (flatten
        [(nth (columns board) x)
         (nth (rows board) y)
         (extract-grid (grid-coord y) (grid-coord x) (rows board))]))))

(quot 5 3)
(grid-coord 5)
(position-info 5 1 puzzle)
(possible-values 5 1 puzzle)
(invalid 4 1 puzzle)

(defn value-at-position [x y board]
  (-> (rows board) (nth y) (nth x)))

(missing (distinct (flatten (valid 4 2 puzzle))))

(missing (distinct (flatten (valid 5 1 puzzle))))

;; stage 1 solver
(for [y (range 0 9)
      x (range 0 9)]
  (let [value (value-at-position x y puzzle)]
  (if (zero? value)
    (possible-values x y puzzle)
    value)))

;; stage 2 solver
(for [y (range 0 9)
      x (range 0 9)]
  (let [value (value-at-position x y puzzle)]
    (let [pos (possible-values x y puzzle)]
      (if (zero? value)
        (if (= 1 (count pos))
          (first pos) 0)
        value))))

(count '(6))

;; solve by pos
;; solve by row, then col?

;; stage 1 solver
(defn solve [board]
  (for [y (range 0 9)
        x (range 0 9)]
    (let [value (value-at-position x y puzzle)]
      (let [pos (possible-values x y puzzle)]
        (if (zero? value)
          (if (= 1 (count pos))
            (first pos) 0)
          value)))))

(solve puzzle)
(6 0 0 4 0 0 1 5 0 9 5 4 7 1 6 0 8 2 0 0 0 5 0 2 6 0 0 8 0 0 0 9 4 0 0 6 0 0 3 8 0 5 4 0 0 4 0 0 3 7 0 0 0 8 0 0 6 9 0 3 0 0 0 0 2 0 0 4 7 8 9 3 0 4 9 0 0 0 0 0 5)
(solve (solve puzzle))
(6 0 0 4 0 0 1 5 0 9 5 4 7 1 6 0 8 2 0 0 0 5 0 2 6 0 0 8 0 0 0 9 4 0 0 6 0 0 3 8 0 5 4 0 0 4 0 0 3 7 0 0 0 8 0 0 6 9 0 3 0 0 0 0 2 0 0 4 7 8 9 3 0 4 9 0 0 0 0 0 5)

(nth (columns puzzle) 0)

(reduce concat (valid 0 2 puzzle))
(flatten (reduce conj (valid 0 2 puzzle)))
(missing (distinct (flatten (into [] (valid 0 2 puzzle)))))

(defn solve [board]
  "Organic or 'indirect' solving method programming ad-hoc ;)"
  ;; First, find any easy pickings - columns with a single missing digit.
  (let []))

(defn -main [& args]
  (println "AHOY!"))

;; ----------------------------------------------------------------------

(map missing (columns puzzle)) ;; missing numbers in each column
(map missing puzzle) ;; missing numbers in each row
(map missing (for [x (range 0 9 3)
                   y (range 0 9 3)]
               (extract-grid x y puzzle))) ;; missing numbers in each grid

(missing [[7 1 3 2 5] [7 4 3 2 9 8] [7 1 3 2 8]]) ;; results for point 0 0 only

;; for each grid
;;   - take the 3 columns that compose it
;;   - take the 3 rows that compose it

(for [x (range 0 9 3)
      y (range 0 9 3)]
  (extract-grid x y puzzle)) ;; all gris
