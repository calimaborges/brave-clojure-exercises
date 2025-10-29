(ns brave-clojure-exercises.ch03-do-things)

;; exercise 1

(str "Hello, " "world!")

(vector 1 2 3)

(list 1 2 3)

(hash-map :a 1 :b 2)

(hash-set 1 2 2 3 4)

;;;;
;; exercise 2

(defn adds100 [n] (+ n 100))

(adds100 2)

;;;;
;; exercise 3

(defn dec-maker [n]
  (fn [x] (- x n)))

(def dec9 (dec-maker 9))

(dec9 10)

;;;;
;; exercise 4
(defn mapset [fn coll]
  (set (map fn coll)))

(mapset inc [1 1 2 2])

;;;;
;; execise 5

(def asym-spyder-body-parts [{:name "head" :size 3}
                             {:name "eye-1" :size 1}
                             {:name "ear-1" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "shoulder-1" :size 3}
                             {:name "upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "forearm-1" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "kidney-1" :size 1}
                             {:name "hand-1" :size 2}
                             {:name "knee-1" :size 2}
                             {:name "thigh-1" :size 4}
                             {:name "lower-leg-1" :size 3}
                             {:name "achiles-1" :size 3}
                             {:name "foot-1" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn numbered-matching-part
  [part num]
  {:name (clojure.string/replace (:name part) #"-1$" (str "-" num))
   :size (:size part)})

(defn is-numbered-part?
  [part]
  (re-find #"-1$" (:name part)))

(defn matching-parts
  [part amount-of-parts]
  (loop [part-number 2
         final-parts [part]]
    (if (or (> part-number amount-of-parts) (not (is-numbered-part? part)))
      final-parts
      (recur (+ part-number 1) (conj final-parts (numbered-matching-part part part-number))))))

(defn symmetrize-body-parts
  [asym-body-parts]
  (reduce (fn [body-parts part]
            (into body-parts (matching-parts part 5))) [] asym-body-parts))

(symmetrize-body-parts asym-spyder-body-parts)

;;;;
;; exercise 6

(defn symmetrize-body-parts
  [asym-body-parts amount-of-parts]
  (reduce (fn [body-parts part]
            (into body-parts (matching-parts part amount-of-parts))) [] asym-body-parts))

(symmetrize-body-parts asym-spyder-body-parts 3)

;;;;
