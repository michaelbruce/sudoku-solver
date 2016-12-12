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
  (apply mapv vector (rows board)))

(columns puzzle)

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

(defn value-at-position [x y board]
  (-> (rows board) (nth y) (nth x)))

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

(defn include? [value col]
  (not (nil? (some #{value} col))))

(defn value-present [value grid-x grid-y puzzle]
  "Returns whether a value exists in each of the rows and columns that make a grid"
  (let [rows (subvec (rows puzzle) grid-x (+ grid-x 3))
        columns (subvec (columns puzzle) grid-y (+ grid-y 3))]
    (concat (map #(include? 6 %) rows)
            (map #(include? 6 %) columns))))

(value-present 6 0 3 puzzle)

(defn grid-solve [grid-x grid-y board]
  "Examine each 3x3 grid, iterate over missing values in that grid and discover
  coordinates that are valid for them by checking which related rows and columns contain a given value."
  (value-present 6 grid-x grid-y board))

;; middle right pos in top middle grid should be 6
(grid-solve 0 3 puzzle)

;; top middle solve 6 pos

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
