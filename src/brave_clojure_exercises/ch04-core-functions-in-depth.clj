(ns brave-clojure-exercises.ch04-core-functions-in-depth)

;; pre-requisites

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int [str] (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(defn convert [vamp-key value] ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn tuple-to-dict
  [dict [key value]]
  (assoc dict key (convert key value)))

(defn generate-key-value-tuple
  [row]
  (map vector vamp-keys row))

(defn convert-row-to-dict
  [row]
  (reduce tuple-to-dict {} (generate-key-value-tuple row)))

(defn mapify
  "Returns a seq of maps like {:name \"Edward Collen\" :glitter-index 10}"
  [rows]
  (map convert-row-to-dict rows))

(mapify (parse (slurp filename)))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def glitter-suspects
  (glitter-filter 3 (mapify (parse (slurp filename)))))

;;;;
;; exercise 1

(map :name (glitter-filter 3 (mapify (parse (slurp filename)))))

;;;;
;; exercise 2

(defn append
  [list suspect]
  (cons suspect list))

(reduce append [] glitter-suspects)

;;;;
;; exercise 3

(def validations {:name some?
                  :glitter-index some?})

(defn validate
  [key suspect]
  ((get validations key) (key suspect)))

(validate :glitter-index {:name "Mr. Borges" :glitter-index 19})

;;;;
;; exercise 4

(defn create-line
  [suspect]
  (str (:name suspect) "," (:glitter-index suspect)))

(defn convert-to-csv
  [suspects]
  (clojure.string/join "\n" (map create-line suspects)))

(convert-to-csv glitter-suspects)

;;;;

