package com.j4_harwood.biocomp.darwin;

public interface Chromosome{
	public void mutate(int idx);
	public Chromosome[] crossover(Chromosome parent1, Chromosome parent2);
	public int getFitness();
	public int maxFitness();
	public int size();
	public Chromosome copy();
}
