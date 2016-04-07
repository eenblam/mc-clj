(ns mc-clj.core)

(defn cumsum-base
  "Basis for the cumsum closure."
  [acc front back]
  (let [new-acc (+ acc (if (empty? back) 0 (first back)))]
    (if (empty? back)
      front
      (recur new-acc
             (concat front [new-acc])
             (rest back)))))

(defn cumsum
  "Return a list providing the cumulative sums of the input collection."
  [coll]
  (cumsum-base 0 [] coll))

(defn transpose
  "Transpose an m-by-n vector of vectors."
  [])

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
