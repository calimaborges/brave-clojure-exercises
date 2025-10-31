;; pre-requisites

(def order-details
  {:name "Michard Blommons"
   :email "michard.blimmonsgmail.com"})

(def order-details-validations
  {:name
   ["Please enter a name" not-empty]

   :email
   ["Please enter an email address" not-empty

    "Your email address doesn't look like and email address"
    #(or (empty? %) (re-seq #"@" %))]})

(defn error-message-for
  "Return a seq of error message"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))

(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-message-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))

(def render println)

;;;;
;; exercise 1

(defmacro when-valid
  [to-validate validations & success-codes]
  `(let [errors-name# (validate ~to-validate ~validations)]
     (when (empty? errors-name#)
       ~@success-codes)))

(when-valid order-details order-details-validations
  (println "It's a success!")
  (render :success))

(def success-order-details
  {:name "Michard Blommons"
   :email "michard.blimmons@gmail.com"})

(when-valid success-order-details order-details-validations
  (println "It's a success!")
  (render :success))

;;;;
;; exercise 2

(defmacro my-or
  ([] nil)
  ([x] x)
  ([x & next]
   `(let [or# ~x]
      (if or# or# (my-or ~@next)))))

(or nil 3 nil)
(my-or nil 3 nil)

;;;;
;; exercise 3

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
               :strength 4
               :dexterity 5}})

(def attr #(comp % :attributes))

(defmacro defattrs
  [& fn-name-attrs-pairs]
  (map (fn [name-attr-pair]
         `(def ~(first name-attr-pair) (attr ~(second name-attr-pair))))
       (partition 2 fn-name-attrs-pairs)))

(defattrs c-int :intelligence
  c-str :strength
  c-dex :dexterity)

(c-int character)
(c-str character)
(c-dex character)

;;;;
