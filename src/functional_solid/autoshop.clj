(ns functional-solid.autoshop
	[:require [functional-solid [car :as car]]])

(defrecord Car [owner mileage lastServiceMileage needsService])

(def cars [(ref (Car. "Bill" 0 0 false))
           (ref (Car. "Milton" 50 0 false))
           (ref (Car. "Peter" 2950 0 false))])

(defn notify [car]
  (println (str (:owner car) "'s car needs service!")))

(defn needsService? [car]
  (>= (- (:mileage car) (:lastServiceMileage car)) 3000))

(defn update-cars [cars mileage-increment]
  (map (fn [car] 
    (dosync
      (alter car car/drive mileage-increment)
      (if (needsService? @car)
        (alter car assoc :needsService true)))) cars))

(defn notify-owners [cars]
	(for [car cars]
		(if (:needsService @car)
			(notify @car))))