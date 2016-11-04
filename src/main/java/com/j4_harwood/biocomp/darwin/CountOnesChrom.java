package com.j4_harwood.biocomp.darwin;

import java.util.Arrays;

public class CountOnesChrom implements Chromosome {
	private final int geneSize = 50;
	private int[] genes = new int[geneSize];
	public CountOnesChrom(){
		this.initialise();
	}
	
	private void initialise(){
		for(int i = 0; i < geneSize; i++){
			genes[i] = newGene();
		}
	}
	
	private int newGene(){
		return GARand.nextInt(2);
	}
	
	public CountOnesChrom(int[] newGenes){
		genes = Arrays.copyOf(newGenes, newGenes.length);
	}

	@Override
	public void mutate(int idx) {
		int prevGene = genes[idx];
		while(prevGene == genes[idx]){
			genes[idx] = newGene();
		}
	}

	@Override
	public CountOnesChrom[] crossover(Chromosome parent1, Chromosome parent2) {
		//Creates 2 NEW children
		int[] p1 = ((CountOnesChrom)parent1).getGenes();
		int[] p2 = ((CountOnesChrom)parent2).getGenes();
		
		int[] c1 = new int[geneSize];
		int[] c2 = new int[geneSize];
		int idx = GARand.nextInt(geneSize);
		for(int i = 0; i < geneSize; i++){
			if(i < idx){
				c1[i] = p1[i];
				c2[i] = p2[i];
			}else{
				c1[i] = p2[i];
				c2[i] = p1[i];
			}
		}
		CountOnesChrom[] children = new CountOnesChrom[2];
		children[0] = new CountOnesChrom(c1);
		children[1] = new CountOnesChrom(c2);
		return children;
	}

	private int[] getGenes() {
		return genes;
	}

	@Override
	public int size() {
		return geneSize;
	}
	
	@Override
	public int getFitness(){
		int fitness = 0;
		for(Integer i : genes){
			if(i == 1){
				fitness++;
			}
		}
		return fitness;
	}
	
	@Override
	public int maxFitness(){
		return geneSize;
	}

	@Override
	public CountOnesChrom copy() {
		CountOnesChrom tChrom = new CountOnesChrom(this.genes);
		return tChrom;
	}
	
	@Override
	public String toString(){
		return Arrays.toString(genes) + " : " + this.getFitness();
	}

}
