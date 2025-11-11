;; exercise 1

(defmulti full-moon-behavior (fn [were-creature] (:were-type were-creature)))

(defmethod full-moon-behavior :ai-entusiast
  [were-creature]
  (str (:name were-creature) " will vibe code"))

(full-moon-behavior {:name "Ailson"
                     :were-type :ai-entusiast})

;;;;
;; exercise 2

(defprotocol WereCreature
  (full-moon-behavior [x]))

(defrecord WereWolf [name title]
  WereCreature
  (full-moon-behavior [x]
    (str name " will howl and murder")))

(defrecord WereSimmons [name title]
  WereCreature
  (full-moon-behavior [x]
    (str name " will encourage people and sweat to the oldies")))

(full-moon-behavior (->WereSimmons "Andy" "The Baker"))

;;;;
;; exericse 3

(defrecord Warrior [name weapon])

(defprotocol Individual
  (attack [i target] "Attacks a target")
  (defend [i target] "Defend from target"))

(extend-type Warrior
  Individual
  (attack [w target] (str (:name w) " attacks " target " with " (:weapon w)))
  (defend [w target] (str (:name w) " defends from " target)))

(def w1 (->Warrior "El Barbaro" "axe"))

(attack w1 "Chupacabra")

(defend w1 "Chupacabra")

(defrecord Mage [name magic])

(defrecord Rogue [name])

(extend-protocol Individual
  Mage
  (attack [m target] (str (:name m) " casts " (:magic m) " in " target))
  (defend [m target] (str (:name m) " defends from " target " with magic"))

  Rogue
  (attack [r target] (str (:name r) " attacks " target " with bow and arrows"))
  (defend [r target] (str (:name r) " defends from " target " using face")))

(def m1 (->Mage "Mr. M" "fire bolt"))

(def r1 (->Rogue "Bullseya"))

(attack m1 "Chupacabra")

(defend m1 "Chupacabra")

(attack r1 "Chupacabra")

(defend r1 "Chupacabra")

;;;;
;; exercise 4

(defmulti cast-spell (fn [mage] (:type mage)))

(defmethod cast-spell :fire
  [mage]
  (str (:name mage) " casts fire bolt"))

(defmethod cast-spell :ice
  [mage]
  (str (:name mage) " casts ice bolt"))

(defmethod cast-spell :arcane
  [mage]
  (str (:name mage) " casts arcane arrow"))

(defmethod cast-spell :lightning
  [mage]
  (str (:name mage) " casts lightning strike"))

(cast-spell {:name "Mr. Hot" :type :fire})

(cast-spell {:name "Mr. Cold" :type :ice})

(cast-spell {:name "Mr. M" :type :arcane})

(cast-spell {:name "Mr. Rain" :type :lightning})

;;;;

