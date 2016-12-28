(ns sudoku-solver.core)

;; A SP is well posed if it has one solutions
;; i.e requires no hinting

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

(defn rows [board]
  (->> board (partition 9) (map vec) (into [])))

(defn cols [board]
  (apply map vector (rows board)))

(defn extract-grid [x y board]
  "Returns a 3x3 grid from a complete sudoku puzzleboard"
  (for [x (range x (+ x 3))
        y (range y (+ y 3))]
    (get-in (rows board) [x y])))

(defn grids [board]
  (for [x (range 0 9 3)
        y (range 0 9 3)]
    (extract-grid x y board)))

(defn validate-group [group]
  (= 1
     (count
      (distinct
       (flatten
        (map (fn [x] (vals (dissoc (frequencies x) 0))) group))))))

(defn update-puzzle [pos val puzzle]
  (let [new-puzzle (assoc puzzle pos val)]
    (if (= true
           (= 0 (nth puzzle pos))
           (validate-group (rows new-puzzle))
           (validate-group (cols new-puzzle))
           (validate-group (grids new-puzzle)))
      new-puzzle puzzle)))

(defn complete-puzzle [puzzle]
  (reduce
   (fn [puzzle [pos val]] (update-puzzle pos val puzzle))
   puzzle (for [x (range 81) y (range 1 10)] (vector x y))))

(def oneiter (complete-puzzle puzzle))

(def teniter (reduce (fn [puzzle x] (complete-puzzle puzzle))
                     puzzle (range 10)))

(= oneiter teniter) ;; true - iteration is off?

(defn -main [& args]
  (println "AHOY!"))
