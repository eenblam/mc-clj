(ns mc-clj.core)

(defn cumprob
  "Return a vector providing the cumulative sums of positive probabilities in coll."
  ([coll] (cumprob coll [] 0))
  ([coll cum-probs acc]
   (let [new-acc (+ acc (if (empty? coll) 0 (first coll)))]
     (if (empty? coll)
       cum-probs
       (recur (rest coll)
              (conj cum-probs
                    ;; 0 if 0, new-acc otherwise
                    (if (= acc new-acc)
                      0
                      new-acc))
              new-acc)))))

(defn make-partition
  [P]
  (vec (map cumprob P)))

(defn normalize-row
  "Normalize row vector to sum to unity."
  [row]
  (vec (map #(/ % (apply + row)) row)))

(defn normalize-matrix
  "Normalize each row of P to sum to unity."
  [P]
  (vec (map normalize-row P)))

(defrecord MC [trans-matrix part-matrix init names])

(defn make-MC
  "Create an MC record from a transition matrix, possibly with initial probabilities or state-names."
  ([P]
   (make-MC P
            (vec (take (count P) (repeat (/ 1 (count P)))))
            []))
  ([P init]
   (make-MC P init []))
  ([P init names]
   (let [P-norm (normalize-matrix P)]
     (->MC P-norm (make-partition P-norm) init names))))

(defn accessible
  "Filters out inaccessible nodes from route mapping."
  [mapping]
  (into {} (filter #(> (second %) 0) mapping)))

(defn next-state
  "Get next state from partition matrix row."
  ([row] (next-state row (rand)))
  ([row p] (next-state row p 0))
  ([row p index]
   (if (<= p (first row))
     index
     (recur (rest row) p (inc index)))))

(defn trans
  "Simulate a single transition from state i given partition matrix M."
  [M i]
  (next-state (get M i)))

(defn simulate-m
  "Simulate n transitions of Markov chain given by partition matrix M, beginning in state i."
  ([M i n] (simulate-m M i n []))
  ([M i n coll]
    (if (<= n 0)
      (conj coll i)
      (recur M (trans M i) (dec n) (conj coll i)))))

(defn simulate-mc
  "Simulate n transitions of Markov chain mc, beginning in state i."
  [^MC mc i n]
  (simulate-m (:part-matrix mc) i n))

(defn simulate-p
  "Simulate n transitions of Markov chain specified by transition matrix P, beginning in state i."
  [P i n]
  (simulate-m (make-partition P) i n))

(defn simulate-rand
  "Simulate n transitions of Markov chain mc, beginning in a random state."
  [^MC mc n]
  (simulate-mc mc (next-state (:init mc)) n))
