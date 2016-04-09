(ns mc-clj.core-test
  (:require [clojure.test :refer :all]
            [mc-clj.core :refer :all]))

(deftest cumprob-test
  (testing "cumprob"
    (testing "on positive integers"
      (is (= [1 3 6] (cumprob [1 2 3]))))
    (testing "with negative integers"
      (is (= [1 -1 2] (cumprob [1 -2 3])))))
    (testing "with lists instead of vectors"
      (is (= [1 3 6] (cumprob '(1 2 3)))))
    (testing "with zero-valued tail"
      (is (= [1 3 0] (cumprob [1 2 0]))))
    (testing "failing with bad arguments"
      (is (thrown? IllegalArgumentException (cumprob 1)))
      (is (thrown? ClassCastException (cumprob "1")))))

(deftest accessible-test
  (testing "accessible"
    (testing "on its happy paths"
      (is (= {:a 0.1, :c 0.9}
             (accessible {:a 0.1, :b 0, :c 0.9}))))))

(deftest next-state-test
  (testing "next-state"
    (testing "on its happy paths"
      (is (= 1 (next-state [0.1 0.5 1.0] 0.5))
          "returns i when p <= i"))))

(deftest simulate-test
  (testing "simulate"
    (testing "singleton class captures state"
      (is (= [0 0 0 0] (simulate-p [[1 0] [0.1 0.9]] 0 3))))))
