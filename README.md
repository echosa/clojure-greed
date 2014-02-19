# greed

This is a Clojure version of the game "greed". The goal of the game is to clear out
as much of the board as possible. When you move in a direction, you move a number
of spaces equal to the first number in that direction. You must be able to move the
full amount. If you can't, you are not allowed to move in that direction. The game
ends when you can no longer make any moves.

You move with the hjklyubn keys:
```
h: left
j: down
k: up
l: right
y: up and left
u: up and right
b: down and left
n: down and right
```

You can toggle showing your available moves by pressing `p`. ***WARNING*** This is known to cause significant lag right now.

You can quit at any time by pressing `q`.

## Installation

Download or clone this repository.

## Usage

You'll need [Leiningen](http://leiningen.org) to run this game. Once you have Leinengen, open a terminal, navigate to this project's directory, then run the app with `lein run`.

You can run the unit tests with `lein test`, and you can get code coverage information with `lein cloverage`.

## Thanks

Thanks to #clojure on Freenode for all your help.
