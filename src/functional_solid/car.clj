(ns functional-solid.car)

(def car {:make "Honda"
          :model "Civic"
          :year 2012
          :mileage 0})

(defn drive [car miles]
  (assoc car :mileage (+ (:mileage car) miles)))