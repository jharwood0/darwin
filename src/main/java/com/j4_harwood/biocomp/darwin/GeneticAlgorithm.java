package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;

public class GeneticAlgorithm<T extends Chromosome<T> & Comparable<T>> {
	private int populationSize;
	private double mutationRate = 0.015;
	private double crossoverRate = 0.8;
	private double elitismRate = 0.15;
	
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

		int numElites = (int) Math.round(elitismRate * (double)populationSize);
		
		for(int i = 0; i < generations; i++){
			if(Evaluate(i)){
				return population;
			}
			
			if(numElites > 0){
				elites = new ArrayList<>();
				for(int j = 0; j < numElites; j++){
					elites.add(this.getFittest().copy());
				}
			}
			
			population = createOffspring();
			
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
		// Should be static, but future problems
		T tChrom = population.get(0);
		for(int i = 0; i < populationSize/2; i++){
			T p1 = tournamentSelection(4);
			T p2 = tournamentSelection(4);
			if(GARand.nextDouble() < crossoverRate){
				T[] children = tChrom.crossover(p1, p2);
				children[0].mutate(mutationRate);
				children[1].mutate(mutationRate);
				newPopulation.add(children[0]);
				newPopulation.add(children[1]);
			}else{
				p1.mutate(mutationRate);
				p2.mutate(mutationRate);
				newPopulation.add(p1.copy());
				newPopulation.add(p2.copy());
			}
		}
		return newPopulation;
	}

	private boolean Evaluate(int generation){
		System.out.println(this.getFittest().getFitness() + " : "+generation);
		if(this.getFittest().getFitness() == this.getFittest().maxFitness()){
			System.out.println("Took : "+generation);
			System.out.println(this.getFittest().toString());
			return true;
		}
		return false;
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
	
	private T tournamentSelection(int numParents){
		ArrayList<T> pool = new ArrayList<T>();
		for(int i = 0; i < numParents; i++){
			pool.add(population.get(GARand.nextInt(populationSize)));
		}
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
		for(int i = 0; i < population.size(); i++){
			value -= population.get(i).getFitness();
			if(value <= 0){
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
	
	private T tournamentSelection(){
			T p1 = population.get(GARand.nextInt(populationSize));
			T p2 = population.get(GARand.nextInt(populationSize));
			if(p1.getFitness() >= p2.getFitness()){
				return p1;
			}else{
				return p2;
			}
	}
	
}
