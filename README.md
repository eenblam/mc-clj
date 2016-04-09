# mc-clj

A simple Clojure library for simulating Markov chains from a transition matrix.

## Usage

```clojure
(def P [[0.1 0 0.9] [0.4 0.2 0.4] [0 1 0]])
(def mc (make-MC P))

;; Start at state 1. Returns vector of 4 states, representing n=3 transitions.
(simulate-p P 1 3)

;; Same, utilizing MC instead
(simulate-mc mc 1 3)

;; Create a new MC record with P(X0=0)=0.1 and P(X0=1)=0.9
(def M [[0.5 0.5] [0.4 0.6]])
(def M-mc M [0.1 0.9])

;; Simulate 10 transitions of M-mc, randomly choosing initial state given [0.1 0.9].
(simulate-rand M-mc 10)

;; TODO: simulate-mc with labeled states.

;; Random walk on the circle with 3 states:
(simulate-p [[0 0.5 0.5] [0.5 0 0.5] [0.5 0.5 0]]
            0
            10)
```

## License

Copyright © 2016 Ben Elam

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
