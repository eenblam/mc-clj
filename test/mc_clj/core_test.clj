(ns mc-clj.core-test
  (:require [clojure.test :refer :all]
            [mc-clj.core :refer :all]))

(deftest cumsum-test
  (testing "Cumsum"
    (testing "on its happy path"
      (is (= '(1 3 6) (cumsum [1 2 3]))))
    (testing "with negative numbers"
      (is (= '(1 -1 2) (cumsum [1 -2 3])))))
    (testing "with lists instead of vectors"
      (is (= '(1 3 6) (cumsum '(1 2 3)))))
    (testing "failing with bad arguments"
      (is (thrown? IllegalArgumentException (cumsum 1)))
      (is (thrown? ClassCastException (cumsum "1")))))
