package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;

public class GeneticAlgorithm<T extends Chromosome<T> & Comparable<T>> {
	
	/* configuration params for the GA environment */
	private int populationSize;
	private double mutationRate = 0.008;
	private double crossoverRate = 0.7;
	private double elitismRate = 0.02;
	
	private ArrayList<T> population = new ArrayList<>();
	private ArrayList<T> elites = new ArrayList<>();
	
	public GeneticAlgorithm(ArrayList<T> population){
		this.population = population;
		this.populationSize = population.size();
	}
	
	public int size(){
		return populationSize;
	}

	public ArrayList<T> evolve(int generations) {
		// calculate how many elites to copy over each generation
		int numElites = (int) Math.round(elitismRate * (double)populationSize);
		System.out.println("Elites : "+numElites);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/* main loop for a generation */
		for(int i = 0; i < generations; i++){
			/* evalutes prints output / checks if fittest has been achieved */
			if(Evaluate(i)){
				return population;
			}
			
			// Copy elites
			if(numElites > 0){
				elites = new ArrayList<>();
				for(int j = 0; j < numElites; j++){
					elites.add(this.getFittest().copy());
				}
			}
			
			// selects parents using rouletteSelection
			// crosses them over based on crossover rate
			// mutates based on mutation rate
			population = createOffspring();
			
			// Copies the elites, overwrites the weakest in the population
			if(numElites > 0){
				for(T elite : elites){
					this.getweakest().replaceGenes(elite);
				}
			}
		}
		System.out.println(this.getFittest().toString());
		return null;
	}
	
	private ArrayList<T> createOffspring(){
		ArrayList<T> newPopulation = new ArrayList<T>();
		
		T tChrom = population.get(0); /* this is a hack because static methods aren't allowed in interfaces in java */
		
		for(int i = 0; i < populationSize/2; i++){
			T p1 = rouletteSelection(); //parent 1
			T p2 = rouletteSelection(); //parent 2
			if(GARand.nextDouble() < crossoverRate){
				// Chromosome.crossover returns an array of 2 children with tails crossed a certain point
				T[] children = tChrom.crossover(p1, p2);
				children[0].mutate(mutationRate);
				children[1].mutate(mutationRate);
				// Add the children to the population
				newPopulation.add(children[0]);
				newPopulation.add(children[1]);
			}else{
				// If crossover rate tells us not to crossover, we'll just mutate based on mutation rate
				p1.mutate(mutationRate);
				p2.mutate(mutationRate);
				
				// add children to population
				newPopulation.add(p1.copy());
				newPopulation.add(p2.copy());
			}
		}
		return newPopulation;
	}

	private boolean Evaluate(int generation){
		System.out.println(generation + ", " + this.totalFitness() + ", "+ this.getFittest().getFitness() + ", "+ this.getweakest().getFitness() + ", "+this.averageFitness());
		//System.out.println(this.getFittest().getFitness() + " : "+generation);
		if(this.getFittest().getFitness() == this.getFittest().maxFitness()){
			System.out.println("Took : "+generation);
			System.out.println(this.getFittest().toString());
			return true;
		}
		return false;
	}

	public int averageFitness(){
		return (int) ((double)this.totalFitness() / (double)population.size());
	}
	
	public T getFittest(){
		T fittest = population.get(0);
		for(T chrom : population){
			if(chrom.getFitness() >= fittest.getFitness()){
				fittest = chrom;
			}
		}
		return fittest;
	}
	
	private T getweakest(){
		T weakest = population.get(0);
		for(T tChrom : population){
			if(tChrom.getFitness() <= weakest.getFitness()){
				weakest = tChrom;
			}
		}
		return weakest;
	}
	
	// tournament selection with multiple parent pool
	private T tournamentSelection(int numParents){
		ArrayList<T> pool = new ArrayList<T>();
		// select N number of parents
		for(int i = 0; i < numParents; i++){
			pool.add(population.get(GARand.nextInt(populationSize)));
		}
		// return the individual with the highest
		T best = pool.get(0);
		for(int i = 0; i < numParents; i++){
			if(pool.get(i).getFitness() >= best.getFitness()){
				best = pool.get(i);
			}
		}
		return best;
	}
	
	private T rouletteSelection(){
		int fitnessSum = totalFitness();
		double value = GARand.nextDouble() * (double)fitnessSum;
		// decrement through population using their fitness
		// this gives a larger probability to individuals with the highest fitness
		// return the selected indvidual when the value <= 0
		for(int i = 0; i < population.size(); i++){
			value -= population.get(i).getFitness();
			if(value <= 0){
				// edge case for when fitness == 0
				return population.get(i);
			}
		}
		return population.get(populationSize-1);
	}
	
	private int totalFitness(){
		int totalFitness = 0;
		for(T chrom : population){
			totalFitness += chrom.getFitness();
		}
		return totalFitness;
	}
	
}
