package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;
import java.util.Collections;

public class GeneticAlgorithm<T extends Chromosome & Comparable<T>> {
	private int populationSize;
	private int geneSize;
	private double mutationRate = 0.015;
	private double crossoverRate = 0.6;
	private double elitismRate = 0.1;
	
	private ArrayList<T> population = new ArrayList<>();
	
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
			
			ArrayList<T> elites = new ArrayList<T>();
			int numElites = (int)(elitismRate * (double)populationSize);
			
			//Collections.sort(population);
			//Collections.reverse(population);
			
			T Elite = getFittest();
			//System.out.println("Best = "+Elite.toString());
			//for(int j = 0; j < numElites; j++){
			//	elites.add(population.get(j));
			//}
			
			
			population = TournamentSelection();
			population = crossoverPopulation();
			mutatePopulation();
			
			//Collections.sort(population);
			//for(int j = 0; j < numElites; j++){
			//population.get(0).replaceGenes(Elite);
			this.getweakest().replaceGenes(Elite);
			//}
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
			
			
//			if(GARand.nextDouble() < this.crossoverRate){
			if(true){
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
