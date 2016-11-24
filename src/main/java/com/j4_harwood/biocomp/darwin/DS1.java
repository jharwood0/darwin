package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DS1 implements Chromosome<DS1>, Comparable<DS1>{
	private final int numRules = 15;
	private final int geneSize = 6*numRules;
	private int fitness = 0;
	
	private static ArrayList<Data<Integer>> inputData;
	
	private int[] genes = new int[geneSize];
	public DS1(){
		this.initialise();
		calcFitness();
	}
	
	private void initialise(){
		for(int i = 0; i < geneSize; i++){
			genes[i] = newGene();
		}
	}
	
	private int newGene(){
		return GARand.nextInt(3);
	}
	
	public DS1(int[] newGenes){
		this.genes = Arrays.copyOf(newGenes, newGenes.length);
		calcFitness();
	}

	@Override
	/*
	 * Loop through genome and replace gene with new one if mutation rate allows
	 */
	public void mutate(double mutationRate) {
		for(int i = 0; i < geneSize; i++){
			double rnd = GARand.nextDouble();
			if(rnd < mutationRate){
				int prevGene = genes[i];
				while(prevGene == genes[i]){
					genes[i] = newGene();
				}
			}
		}
		calcFitness();
	}

	@Override
	/*
	 * creates random point for crossover
	 * loops through genome, if i is less that crossover point, copy parent gene into
	 * child gene, if i is greater than, then swap the parents round.
	 * return the two children
	 */
	public DS1[] crossover(DS1 parent1, DS1 parent2) {
		//Creates 2 NEW children
		int[] p1 = parent1.getGenes();
		int[] p2 = parent2.getGenes();
		
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
		DS1[] children = new DS1[2];
		children[0] = new DS1(c1);
		children[1] = new DS1(c2);
		return children;
	}

	private int[] getGenes() {
		return genes;
	}

	@Override
	public int size() {
		return geneSize;
	}
	
	private void importRules(){
		inputData = new ArrayList<Data<Integer>>();
		Scanner scanner = new Scanner(getClass().getResourceAsStream("DS1.train"));
		String p = "(\\d)(\\d)(\\d)(\\d)(\\d)\\s(\\d)";
		Pattern pattern = Pattern.compile(p);
		while(scanner.hasNextLine()){
			//System.out.println(scanner.nextLine());
			Matcher matcher = pattern.matcher(scanner.nextLine());
			if(matcher.find()){
				Integer[] inputs = new Integer[5];
				inputs[0] = Integer.parseInt(matcher.group(1));
				inputs[1] = Integer.parseInt(matcher.group(2));
				inputs[2] = Integer.parseInt(matcher.group(3));
				inputs[3] = Integer.parseInt(matcher.group(4));
				inputs[4] = Integer.parseInt(matcher.group(5));
				int output = Integer.parseInt(matcher.group(6));
				Data<Integer> tData = new Data<Integer>(inputs, output);
				inputData.add(tData);
			}
		}
		scanner.close();
	}
	
	private ArrayList<Data<Integer>> parseGenes(){
		ArrayList<Data<Integer>> generatedRules = new ArrayList<Data<Integer>>();
		for(int i = 0; i < geneSize;){
			int j = 0;
			Integer inputs[] = new Integer[5];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			int output = genes[i++];
			Data<Integer> tData = new Data<Integer>(inputs, output);
			generatedRules.add(tData);
		}
		
		return generatedRules;
	}
	
	@Override
	public int getFitness(){
		
		return fitness;
	}
	
	private void calcFitness(){
		if(inputData == null){
			importRules();
		}
		int tfitness = 0;
		ArrayList<Data<Integer>> generatedRules = parseGenes();
		for(Data<Integer> input : inputData){
			for(Data<Integer> generatedRule : generatedRules){
				if(generatedRule.matches(input)){
					if(generatedRule.getOutput() == input.getOutput()){
						tfitness ++;
					}
					break;
				}
			}
		}
		fitness = tfitness;
	}
	
	@Override
	public int maxFitness(){
		return 32;
	}

	@Override
	public DS1 copy() {
		DS1 tChrom = new DS1(this.genes);
		return tChrom;
	}
	
	@Override
	public String toString(){
		return Arrays.toString(genes) + " : " + this.getFitness();
	}

	@Override
	public void replaceGenes(DS1 tChrom) {
		int[] newGenes = Arrays.copyOf(tChrom.getGenes(), geneSize);
		this.genes = newGenes;
		calcFitness();
	}


	@Override
	public int compareTo(DS1 o) {
		if(o.getFitness() == this.getFitness()){
			return 0;
		}
		if(this.getFitness() < o.getFitness()){
			return -1;
		}else{
			return 1;
		}
	}

}
