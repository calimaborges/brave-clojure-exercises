;; exercise 1

(def counter (atom 0))

(swap! counter inc)

@counter

;; exercise 2
;; www.braveclojure.com/random-quote is not working.
;; So we are using: https://api.adviceslip.com/advice
(require '[clojure.string :as str])

(def random-quote-url "https://api.adviceslip.com/advice")

(defn count-words
  [phrase]
  (reduce (fn [acc word]
            (update-in acc [word] (fnil inc 0)))
          {}
          (str/split phrase #" ")))

(defn fetch-and-count!
  [word-count]
  (let [random-quote (slurp random-quote-url)]
    (swap! word-count (fn [current-state] (merge-with current-state (count-words random-quote))))))

(defn quote-word-count
  [num]
  (let [word-count (atom {})
        random-quote-p (doall (repeatedly num #(future (fetch-and-count! word-count))))]
    (run! deref random-quote-p)
    @word-count))

(quote-word-count 2)

;;;;
;; exercise 3

(defn character-validator
  [character]
  (println character)
  (let [healing-potion-count (get-in character [:inventory :healing-potion])]
    (or (>= healing-potion-count 0)
        (throw (IllegalStateException. "Not enough healing potions")))))

(def warrior (ref {:max-hp 40 :hp 15}))

(def healer (ref {:inventory {:healing-potion 1}} :validator character-validator))

(defn use-potion!
  [healer target]
  (dosync
   (alter healer update-in [:inventory :healing-potion] dec)
   (alter target assoc-in [:hp] (:max-hp @target))))

(use-potion! healer warrior)

@healer

@warrior

