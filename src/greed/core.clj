(ns greed.core
  (:require [greed.coords :refer :all])
  (:gen-class))

(def player-character 
  "The character that represents the player."
  "@")

(defn get-thing-at-grid-position [grid position]
  "Returns whatever is at (X Y) in the grid or nil if bad (X Y) is given."
  (let [x (first position)
        y (try
            (nth position 1)
            (catch NullPointerException e nil))]
    (when y
      (try
        (nth (nth grid y) x)                        
        (catch IndexOutOfBoundsException e nil)))))

(defn find-player [grid]
  "Returns the (X Y) position of the player."
  (let [row (first (filter #(> (.indexOf % player-character) -1)  grid))]
    (list (.indexOf row player-character) (.indexOf grid row))))

(defn traverse-grid-in-direction 
  "Traverses the gride in the given direction."
  ([grid position direction]
     (let [thing-at-first-move (get-thing-at-grid-position
                                grid
                                (get-next-coordinate grid position direction))
           max-moves (if thing-at-first-move thing-at-first-move 0)]
       (traverse-grid-in-direction grid position direction max-moves 0)))

  ([grid position direction max-moves moves-made]
     (let [next-move (get-next-coordinate grid position direction)]
       (if (and next-move
                (not (= (get-thing-at-grid-position grid next-move) 0))
                (< moves-made max-moves))
         (traverse-grid-in-direction grid next-move direction max-moves
                                     (+ moves-made 1))
         {:position position :moves-made moves-made}))))

(defn check-player-move-in-direction [grid player-position direction]
  (let [move-amount (get-thing-at-grid-position
                     grid (get-next-coordinate grid player-position direction))
        moves-made ((traverse-grid-in-direction grid player-position direction) :moves-made)]
    (and (not (= move-amount 0))
         (= move-amount moves-made))))

(defn get-player-moves [grid]
  "Returns keywords for the directions in which a player can move."
  (let [player-position (find-player grid)
        moves (list)]
    (list
     (when (check-player-move-in-direction grid player-position :northwest)
       :northwest)
     (when (check-player-move-in-direction grid player-position :north)
       :north)
     (when (check-player-move-in-direction grid player-position :northeast)
       :northeast)
     (when (check-player-move-in-direction grid player-position :west)
       :west)
     (when (check-player-move-in-direction grid player-position :southwest)
       :southwest)
     (when (check-player-move-in-direction grid player-position :south)
       :south)
     (when (check-player-move-in-direction grid player-position :southeast)
       :southeast)
     (when (check-player-move-in-direction grid player-position :east)
       :east))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
