package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;

public class GeneticAlgorithm<T extends Chromosome & Comparable<T>> {
	private int populationSize;
	private double mutationRate = 0.015;
	private double crossoverRate = 0.5;
	private boolean elitism = true;
	
	private ArrayList<T> population = new ArrayList<>();
	T Elite = null;
	
	public GeneticAlgorithm(ArrayList<T> population){
		this.population = population;
		this.populationSize = population.size();
	}
	
	public int size(){
		return populationSize;
	}

	public ArrayList<T> evolve(int generations) {
		for(int i = 0; i < generations; i++){
			if(Evaluate(i)){
				return population;
			}
			
			if(elitism){
				Elite = getFittest();
			}
			// Includes crossover, 
			//population = createOffspring();
			population = crossoverPopulation();
			mutatePopulation();
			
			if(elitism){
				this.getweakest().replaceGenes(Elite);
			}
		}
		return null;
	}
	

	private ArrayList<T> crossoverPopulation() {
		ArrayList<T> newPopulation = new ArrayList<T>();
		// Should be static, but future problems
		T tChrom = population.get(0);
		
		for(int i = 0; i < populationSize/2; i++){
			T p1 = tournamentSelection();
			T p2 = tournamentSelection();
			if(GARand.nextDouble() < crossoverRate){
				T[] children = (T[])tChrom.crossover(p1, p2);
				newPopulation.add(children[0]);
				newPopulation.add(children[1]);
			}else{
				newPopulation.add((T)p1.copy());
				newPopulation.add((T)p2.copy());
			}
		}
		return newPopulation;
	}

	private boolean Evaluate(int generation){
		System.out.println(this.getFittest().getFitness() + " : "+generation);
		if(this.getFittest().getFitness() == this.getFittest().maxFitness()){
			System.out.println("Took : "+generation);
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
	
	private void mutatePopulation(){
		for(T tChrom : population){
			tChrom.mutate(mutationRate);
		}
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
