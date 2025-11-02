;; Most search engines now depend on JavaScript and are not pure HTML,
;; so we couldn't do the exercise as it was supposed to be done.
;; Instead, we just took some stable pages that contain links
;; and used them.

;; exercise 1
(def cern-base "https://info.cern.ch/hypertext/WWW")

(def cern-projects (str cern-base "/TheProject.html"))

(def cern-status (str cern-base "/Status.html"))

(defn links-from-html
  [html-string]
  (let [link-pattern #"(?i)HREF=\"?([^\"\s>]*)\"?"]
    (map second (re-seq link-pattern html-string))))

(defn fetch-first-html-from-link
  [link]
  (let [html (slurp link)
        first-link (first (links-from-html html))]
    (future (slurp (str cern-base "/" first-link)))))

(defn fetch-links
  []
  (let [projects (fetch-first-html-from-link cern-projects)
        status (fetch-first-html-from-link cern-status)]
    (map deref [projects status])))

(def result (fetch-links))
(first result)
(second result)

;;;;
;; exercise 2
;; depends on previous exercise functions: links-from-html fetch-first-html-from-link

(defn fetch-links
  [& links]
  (let [first-links (map fetch-first-html-from-link links)]
    (map deref first-links)))



(def result (fetch-links cern-projects cern-status))

(first result)
(second result)

;;;;
;; exercise 3
;; "terms" is a list of page filenames from the CERN WWW project.
;; Base page: https://info.cern.ch/hypertext/WWW/TheProject.html
;; Examples: "Bibliography.html", "People.html", "Help.html"

(defn fetch-page-links
  [link]
  (future (let [html (slurp link)]
            (links-from-html html))))

(defn fetch-links
  [& terms]
  (let [futures (doall (map #(fetch-page-links (str cern-base "/" % ".html")) terms))]
    (map deref futures)))

(time (first (fetch-links "Bibliography" "People" "Help")))
(time (second (fetch-links "Bibliography" "People" "Help")))
(time (nth (fetch-links "Bibliography" "People" "Help") 2))

(time (println (fetch-links "Bibliography")))
(time (println (fetch-links "Bibliography" "People")))
(time (println (fetch-links "Bibliography" "People" "Help")))

;;;;
