# mine-sweeper

This is a simple mine-sweeper-like game, created for the demo-purposes. 
It does not contain fully interactive console (albeit making it from this implementation is trivial).
The project is organized as a maven multi-module project with two artifacts: console and game.
The game module is an independent 'game engine' and can be used by some sort of interactive module (console, fancy UI etc).

## Prerequisites
Java 11, Maven.

## Usage

- build:

  mvn install

- run the demo (creates a board of given dimensions and number of black holes, then emulates one 'open interaction' with random cell):


  cd console/target &&
  java -jar msw.jar 5 10

This example will run the 5 x 5 board with 10 black holes and randomly 'opens' one cell. Please do not use dimension greater than 60.

