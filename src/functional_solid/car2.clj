(ns functional-solid.car2)

(def car {:make "Honda"
          :model "Civic"
          :year 2012
          :mileage 0})

(defn inc-map-value [map key inc-value]
	(assoc map key (+ (key map) inc-value)))

(defn drive [car miles]
	(inc-map-value car :mileage miles))

(defn climb [elevator floors]
	(inc-map-value elevator :floor floors))