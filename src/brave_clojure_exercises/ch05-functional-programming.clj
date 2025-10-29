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
;; exercise 3

(def character
  {:name "Smooches McCutes"
   :stats {:attributes {:intelligence 10
                        :strength 4
                        :dexterity 5}}})

(defn my-assoc-in
  [m [k & ks] v]
  (if (empty? ks)
    (assoc m k v)
    (assoc m k (my-assoc-in (k m) ks v))))

(my-assoc-in character [:stats :attributes :vitality] 10)

;;;;
;; exercise 4

(update-in character [:stats :attributes :dexterity] + 2)

;;;;
;; exercise 5

(defn my-update-in
  [m ks updater & args]
  (assoc-in m ks (apply updater (get-in m ks) args)))

(my-update-in character [:stats :attributes :dexterity] + 2)

(def character
  {:name "Smooches McCutes"
   :stats {:attributes {:intelligence 10
                        :strength 4
                        :dexterity 5}}})

(defn my-assoc-in
  [m [k & ks] v]
  (if (empty? ks)
    (assoc m k v)
    (assoc m k (my-assoc-in (k m) ks v))))

(my-assoc-in character [:stats :attributes :vitality] 10)

;;;;
;; exercise 4

(update-in character [:stats :attributes :dexterity] + 2)

;;;;
;; exercise 5

(defn my-update-in
  [m ks updater & args]
  (assoc-in m ks (apply updater (get-in m ks) args)))

(my-update-in character [:stats :attributes :dexterity] + 2)

;;;;
