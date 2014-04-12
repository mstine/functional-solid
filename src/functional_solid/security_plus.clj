(ns functional-solid.security-plus)

(def door (ref {:open false
		        :tids '()}))

(defn open [door]
	(dosync (alter door assoc :open true)))

(defn close [door]
	(dosync (alter door assoc :open false)))

(defn timed-close [door]
	(dosync
		(close door)
		(alter door assoc :tids (rest (:tids @door)))))

(defn create-facts [door tid]
	{:open-door   (:open @door)
	 :last-tid    (first (:tids @door))
	 :current-tid tid})

(defn apply-rules [facts]
	(and (:open-door facts)
	 	 (= (:last-tid facts) (:current-tid facts))))

(defn action-applied? [door tid action]
	(let [facts (create-facts door tid)]
		(if (apply-rules facts)
			(do (action) true)
			 false)))

(defn timed-open [door timeout action]
	(let [tid (java.util.UUID/randomUUID)]
		(future
			(dosync
				(open door)
				(alter door assoc :tids (cons tid (:tids @door))))
				(Thread/sleep timeout)
				(if (action-applied? door tid action)
					(dosync (alter door assoc :tids 
						(rest (:tids @door))))))))

(defn shoot-laser-beams []
	(println "LASER BEAMS!!!"))

(defn first-test []
	(timed-close door)
	(timed-open door 5000 shoot-laser-beams))

(defn second-test []
	(timed-close door)
	(timed-open door 5000 shoot-laser-beams)
	(println (deref door))
	(timed-close door))

(defn third-test []
	(timed-close door)
	(println (str "First close: " @door))
	(timed-open door 5000 shoot-laser-beams)
	(Thread/sleep 500)
	(println (str "First open: " @door))
	(timed-close door)
	(Thread/sleep 500)
	(println (str "Second close: " @door))
	(timed-open door 5000 shoot-laser-beams)
	(Thread/sleep 500)
	(println (str "Last open: " @door)))
