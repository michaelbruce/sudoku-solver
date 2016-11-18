(set-env!
  :source-paths #{"src/clj"}
  :dependencies '[[org.clojure/clojure "1.8.0"]])

(deftask build
  "Builds an uberjar of this project that can be run with java -jar"
  []
  (comp
    (aot :namespace '#{sudoku-solver.core})
    (pom :project 'sudoku-solver
         :version "0.0.0")
    (uber)
    (jar :main 'sudoku-solver.core)
    (target)))

;; Only works in cider
;; Produces ClassNotFoundException from the cli as 'boot run'
;; (deftask run
;;   "Runs a built target jar"
;;   []
;;   (require 'sudoku-solver.core)
;;   (sudoku-solver.core/-main))
