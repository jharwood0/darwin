package com.j4_harwood.biocomp.darwin;

public interface Chromosome<T>{
	public void mutate(double mutationRate);
	public T[] crossover(T parent1, T parent2);
	public int getFitness();
	public int maxFitness();
	public int size();
	public T copy();
	public void replaceGenes(T tChrom);
}
