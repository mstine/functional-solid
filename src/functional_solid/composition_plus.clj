(ns functional-solid.composition-plus)

(defrecord Foo [lifeStatus])

(def foos [(ref (Foo. "alive"))
		   (ref (Foo. "alive"))
		   (ref (Foo. "alive"))])

(defn kill [foo-ref]
	(dosync (alter foo-ref assoc :lifeStatus "dead")))

(defn maim [foo-ref]
	(dosync (alter foo-ref assoc :lifeStatus "wounded")))

(map maim foos)
(map kill foos)