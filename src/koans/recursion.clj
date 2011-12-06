(defn is-even? [n]
  (if (= n 0)
    true
    (not (is-even? (dec n)))))

(defn is-even-bigint? [n]
  (loop [n   n
         acc true]
    (if (= n 0)
      acc
      (recur (dec n) (not acc)))))

;; This only works because coll is a vector and peek on a vector peeks
;; the last element
;;
;; peek on a list peeks the first element
;;
;; pop and peek always access most efficient location (for vector or list)
;;
(defn recursive-reverse [coll]
  (if (empty? coll)
    coll
    (cons (peek coll) (recursive-reverse (pop coll)))))

;; this will work for either list or vector
(defn recursive-reverse-any [coll]
  (if (empty? coll)
    coll
    (concat (recursive-reverse-any (rest coll)) (list (first coll)))))

(defn factorial [n]
  (if (= n 0)
    1
    (* n (factorial (dec n)))))

(defn factorial' [n]
  (loop [n n
         acc 1]
    (if (= n 0)
      acc
      (recur (dec n) (* acc n)))))

(meditations
  "Recursion ends with a base case"
  (= true (is-even? 0))

  "And starts by moving toward that base case"
  (= false (is-even? 1))

  "Having too many stack frames requires explicit tail calls with recur"
  (= false (is-even-bigint? 100003N))

  "Reversing directions is easy when you have not gone far"
  (= '(1) (recursive-reverse [1]))

  "Yet more difficult the more steps you take"
  (= '(5 4 3 2 1) (recursive-reverse [1 2 3 4 5]))

  "Doesn't work on lists"
  (= '(1 2 3 4 5) (recursive-reverse '(1 2 3 4 5)))

  "Working on vectors"
  (= '(5 4 3 2 1) (recursive-reverse-any [1 2 3 4 5]))

  "Working on lists"
  (= '(5 4 3 2 1) (recursive-reverse-any '(1 2 3 4 5)))

  "Simple things may appear simple."
  (= 1 (factorial 1))

  "They may require other simple steps."
  (= 2 (factorial 2))

  "Sometimes a slightly bigger step is necessary"
  (= 6 (factorial 3))

  "And eventually you must think harder"
  (= 24 (factorial 4))

  "Special works as normal"
  (= 24 (factorial' 4))

  "You can even deal with very large numbers"
  (< 1000000000000000000000000N (factorial' 1000N))

  "But what happens when the machine limits you?"
  (< 1000000000000000000000000N (factorial' 10000N)))
