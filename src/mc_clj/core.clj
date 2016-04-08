(ns mc-clj.core)

(defn cumprob-base
  "Basis for the cumprob closure."
  [acc front back]
  (let [new-acc (+ acc (if (empty? back) 0 (first back)))]
    (if (empty? back)
      front
      (recur new-acc
             (conj front
                   ;; 0 if 0, new-acc otherwise
                   (if (= acc new-acc)
                     0
                     new-acc))
             (rest back)))))

(defn cumprob
  "Return a list providing the cumulative sums of positive probabilities in coll."
  [coll]
  (cumprob-base 0 [] coll))

(defn accessible
  "Filters out inaccessible nodes from route mapping."
  [mapping]
  (into {} (filter #(> (second %) 0) mapping)))

(defn next-state-base
  "Basis for the next-state closure."
  [p cumprobs index]
  (if (<= p (first cumprobs))
    index
    (recur p (rest cumprobs) (inc index))))

(defn next-state-of-sums
  "Get next state from cumprobs collection."
  [p cumprobs]
  (next-state-base p cumprobs 0))

(defn next-state
  "Get next state from transition matrix row."
  [row]
  (next-state-of-sums (rand) (cumprob row)))

(defn trans
  "Simulate a single transition from state i given transition matrix P."
  [P i]
  (next-state (get P i)))

(defn simulate-base
  "Basis for simulate clojure."
  [P i n coll]
  (if (<= n 0)
    (conj coll i)
    (recur P (trans P i) (dec n) (conj coll i))))

(defn simulate
  "Simulate n transitions of Markov chain given by P, beginning in state i."
  [P i n]
  (simulate-base P i n []))

(defn simulate-rand
  "Simulate n transitions of Markov chain given by P, beginning in a random state."
  [P n]
  (simulate P (rand-int (count P)) n))
