# mc-clj

A simple Clojure library for simulating Markov chains from a transition matrix.

## Usage

```clojure
(def P [[0.1 0 0.9] [0.4 0.2 0.4] [0 1 0]])

;; Start at state 1. Returns vector of 4 states, representing n=3 transitions.
(simulate P 1 3)

;; Pick random starting state.
;; Returns vector of 5 states, representing n=4 transitions.
(simulate-rand P 4)

;; Random walk on the circle with 3 states:
(simulate
  [[0 0.5 0.5] [0.5 0 0.5] [0.5 0.5 0]]
  0
  10)
```

## License

Copyright © 2016 Ben Elam

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
