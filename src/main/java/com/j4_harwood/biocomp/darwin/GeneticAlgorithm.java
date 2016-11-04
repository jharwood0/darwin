package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;

public class GeneticAlgorithm<T extends Chromosome> {
	private int populationSize;
	private int geneSize;
	private double mutationRate = 0.015;
	private double crossoverRate = 0.7;
	
	private ArrayList<T> population = new ArrayList<>();;
	public GeneticAlgorithm(ArrayList<T> population){
		this.population = population;
		this.populationSize = population.size();
		this.geneSize = population.get(0).size();
	}
	
	public int size(){
		return populationSize;
	}

	public ArrayList<T> evolve(int generations) {
		for(int i = 0; i < generations; i++){
			if(Evaluate(i)){
				return population;
			}
			population = TournamentSelection();
			population = crossoverPopulation();
			mutatePopulation();
		}
		return null;
	}
	
	private ArrayList<T> crossoverPopulation() {
		ArrayList<T> newPopulation = new ArrayList<T>();
		// Should be static, but future problems
		T tChrom = population.get(0);
		
		for(int i = 0; i < populationSize/2; i++){
			T p1 = population.get(GARand.nextInt(populationSize));
			T p2 = population.get(GARand.nextInt(populationSize));
			if(GARand.nextDouble() < this.crossoverRate){
				//System.out.println("Parent 1 :"+p1.toString());
				//System.out.println("Parent 2 :"+p2.toString());
				T[] children = (T[]) tChrom.crossover(p1, p2);
				newPopulation.add(children[0]);
				newPopulation.add(children[1]);
				//System.out.println("C 1 :"+children[0].toString());
				//System.out.println("C 2 :"+children[1].toString());
			}else{
				newPopulation.add(p1);
				newPopulation.add(p2);
			}
		}
		return newPopulation;
	}

	private boolean Evaluate(int generation){
		System.out.println(this.getFittest().toString());
		if(this.getFittest().getFitness() == this.getFittest().maxFitness()){
			System.out.println("Took : "+generation);
			return true;
		}
		return false;
	}
	
	private T getFittest(){
		T fittest = population.get(0);
		for(T chrom : population){
			if(chrom.getFitness() >= fittest.getFitness()){
				fittest = chrom;
			}
		}
		return fittest;
	}
	
	private ArrayList<T> rouletteSelection(){
		int j = 0;
		ArrayList<T> selectedParents = new ArrayList<T>();
		
		while(j != populationSize){
			int fitnessSum = totalFitness();
			double value = GARand.nextDouble() * (double)fitnessSum;
			boolean roundingError = true;
			for(int i = 0; i < population.size(); i++){
				value -= population.get(i).getFitness();
				if(value <= 0){
					T tChrom = (T) population.get(i).copy();
					selectedParents.add(tChrom);
					roundingError = false;
					break;
				}
			}
			if(roundingError){
				T tChrom = (T) population.get(populationSize -1).copy();
				selectedParents.add(tChrom);
			}
			j++;
		}
		return selectedParents;
	}
	
	private void mutatePopulation(){
		for(T tChrom : population){
			for(int i = 0; i < geneSize; i++){
				if(GARand.nextDouble() < mutationRate){
					tChrom.mutate(i);
				}
			}
		}
	}
	
	private int totalFitness(){
		int totalFitness = 0;
		for(T chrom : population){
			totalFitness += chrom.getFitness();
		}
		return totalFitness;
	}
	
	private ArrayList<T> TournamentSelection(){
		ArrayList<T> newPop = new ArrayList<T>();
		for(int i = 0; i < populationSize; i ++){
			T p1 = population.get(GARand.nextInt(populationSize));
			T p2 = population.get(GARand.nextInt(populationSize));
			if(p1.getFitness() >= p2.getFitness()){
				newPop.add(p1);
			}else{
				newPop.add(p2);
			}
		}
		return newPop;
	}
	
}
