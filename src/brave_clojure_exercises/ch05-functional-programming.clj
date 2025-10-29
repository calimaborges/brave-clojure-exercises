;; pre-requisites

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
               :strength 4
               :dexterity 5}})

;;;;
;; exercise 1

(def attr #(comp % :attributes))

(def c-int (attr :intelligence))

(c-int character)

;;;;
;; exercise 2

(defn my-comp
  ([] identity)
  ([& functions]
   (fn [& args]
     (reduce (fn [result function] (function result))
             (apply (last functions) args)
             (reverse (butlast functions))))))

(def test (my-comp inc +))

(test 1 2 3)

;;;;

