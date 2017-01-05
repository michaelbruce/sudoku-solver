(set-env!
  :source-paths #{"src/clj"}
  :dependencies '[[org.clojure/clojure "1.8.0"]])

(deftask build
  "Builds an uberjar of this project that can be run with java -jar"
  []
  (comp
    (aot :namespace #{'sudoku-solver.core})
    (uber)
    (jar :file "solver.jar" :main 'sudoku-solver.core)
    (sift :include #{#"solver.jar"})
    (target)))

(deftask run
  "Runs a built target jar"
  []
  (require 'sudoku-solver.core)
  (let [core-main (resolve 'sudoku-solver.core/-main)]
    (core-main)))
