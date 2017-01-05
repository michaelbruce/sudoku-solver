(ns sudoku-solver.core
  (:require [clojure.set :as sets]
            [clojure.pprint :as pp])
  (:gen-class))

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

(defn potential-values [group1 group2 group3]
  (sets/difference (set (range 1 10))
                   (set group1)
                   (set group2)
                   (set group3)))

(defn y-pos [num-val]
  (int (/ num-val 9)))

(defn x-pos [num-val]
  (mod num-val 9))

(defn gpos [val]
  "Gets the starting grid position for a given x or y"
  (* (int (/ val 3)) 3))

(defn values-for-pos [pos puzzle]
  (potential-values (nth (rows puzzle) (y-pos pos))
                    (nth (cols puzzle) (x-pos pos))
                    (extract-grid (gpos (y-pos pos))
                                  (gpos (x-pos pos)) puzzle)))

(defn solve [puzzle]
  (reduce (fn [puzzle x]
            (if (or (= 0 (nth puzzle x))
                    (set? (nth puzzle x)))
              (let [possible-values (values-for-pos x puzzle)]
                (if (< 1 (count possible-values))
                  (assoc puzzle x possible-values)
                  (assoc puzzle x (first possible-values))))
              puzzle))
          puzzle
          (range 81)))

(defn complete? [puzzle]
  (and (empty? (filter set? puzzle))
       (not= 0 (some #{0} puzzle))))

(defn complete-puzzle [puzzle]
  (reduce (fn [puzzle x]
            (if (complete? puzzle)
              (reduced puzzle)
              (solve puzzle)))
          puzzle (range)))

(defn -main [& args]
  (time (pp/pprint (rows (complete-puzzle puzzle)))))
