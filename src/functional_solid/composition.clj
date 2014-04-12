(ns functional-solid.composition
	[:use [clojure.string :only [join split]]])

(defn useful-utility-function [foos]
	(join "," foos))

(def my-foos ["foo1","foo2","foo3"])

(defn tokenize-the-useful-string [foos]
	(split (useful-utility-function foos) #","))

(tokenize-the-useful-string my-foos)