# Biocomputation Assignment 1
Darwin is a multipurpose Genetic algorithm designed to investigate the useful of EA's within classification.

## Features:
* Roulette Selection
* Crossover probability
* Mutation probability
* Elitism rate

## DataSets
* DS1 = 5-bit binary input with 2 output classes
* DS2 = 6-bit binary input with 2 output classes
* DS3 = 6 floating point value input with 2 output classes
* SPECT = https://archive.ics.uci.edu/ml/machine-learning-databases/spect/

## Structure
GeneticAlgorithm.java is the GA's environment, it can be initalised with an arraylist of objects that implement the Chromosome.java interface.

TestEngine*.java are used to test an individuals gene against a testset.

Data.java and BoundedData.java are encodings for the genome, they are responsible for converting a genome into a working rule set.

## Project
This is an eclipse project using the Maven dependency manager.