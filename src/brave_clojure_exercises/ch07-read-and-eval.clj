;; exercise 1

(eval (read-string "(list (quote (\"Carlos\" \"Interstellar\")))"))

;;;;
;; exercise 2

(defn find-indexes
  [pred coll]
  (keep-indexed #(when (pred %2) %1) coll))

(defn find-next-op-idx
  [coll]
  (let [additive-indexes (find-indexes #(or (= % '-) (= % '+)) coll)
        multiplicative-indexes (find-indexes #(or (= % '*) (= % '/)) coll)]
    (if (seq additive-indexes)
      (apply max additive-indexes)
      (if (seq multiplicative-indexes)
        (apply max multiplicative-indexes)))))

(defn infix->prefix
  [infix]
  (if (= 1 (count infix))
    (first infix)
    (let [op-idx (find-next-op-idx infix)
          op (nth infix op-idx)
          left (take op-idx infix)
          right (drop (inc op-idx) infix)]
      (list op (infix->prefix left) (infix->prefix right)))))

(defmacro infix
  [infixed]
  (infix->prefix infixed))

(infix (1 + 2 * 3 + 4 / 2 + 10))

