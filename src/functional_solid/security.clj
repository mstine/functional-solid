(ns functional-solid.security)

(def door (ref {:open false}))

(defn open [door]
	(dosync (alter door assoc :open true)))

(defn close [door]
	(dosync (alter door assoc :open false)))

(defn timed-open [door timeout callback]
	(future
		(open door)
		(Thread/sleep timeout)
		(if (:open @door) (callback))))

(defn shoot-laser-beams []
	(println "LASER BEAMS!!!"))