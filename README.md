# greed

This is a Clojure version of the game "greed". The goal of the game is to clear out
as much of the board as possible. When you move in a direction, you move a number
of spaces equal to the first number in that direction. You must be able to move the
full amount. If you can't, you are not allowed to move in that direction. The game
ends when you can no longer make any moves.

You move with the following keys:
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

You can toggle showing your available moves by pressing `p`.
*Showing available moves is currently known to cause lag.*

You can quit at any time by pressing `q`.

## Installation

Download or clone this repository.

## Usage

You'll need [Leiningen](http://leiningen.org) to run this game. Once you have
Leinengen, open a terminal, navigate to this project's directory, then run the
app with `lein run`.

If you want to run all code checks and tests, you can do so with make via
`make check` or `make coverage`. The latter will also include code coverage
reports.

You can run just the unit tests with `lein test`, and you can get code coverage
information with `lein cloverage`.

Similarly, you can run just the type checking with `lein typed check`. You can
also run `lein typed coverage` for a coverage report.

## Thanks

Thanks to #clojure on Freenode for all your help.

Thanks to Ambrose for all the help with [core.typed](https://github.com/clojure/core.typed)!
