package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DS2 implements Chromosome<DS2>, Comparable<DS2>{
	private final int numRules = 5;
	private final int geneSize = 7*numRules;
	
	private static ArrayList<Data<Integer>> inputData;
	
	private int[] genes = new int[geneSize];
	public DS2(){
		this.initialise();
	}
	
	private void initialise(){
		for(int i = 0; i < geneSize; i++){
			genes[i] = newGene();
		}
	}
	
	private int newGene(){
		return GARand.nextInt(3);
	}
	
	public DS2(int[] newGenes){
		genes = Arrays.copyOf(newGenes, newGenes.length);
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
	}

	@Override
	/*
	 * creates random point for crossover
	 * loops through genome, if i is less that crossover point, copy parent gene into
	 * child gene, if i is greater than, then swap the parents round.
	 * return the two children
	 */
	public DS2[] crossover(DS2 parent1, DS2 parent2) {
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
		DS2[] children = new DS2[2];
		children[0] = new DS2(c1);
		children[1] = new DS2(c2);
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
		Scanner scanner = new Scanner(getClass().getResourceAsStream("DS2.train"));
		String p = "(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)\\s(\\d)";
		Pattern pattern = Pattern.compile(p);
		while(scanner.hasNextLine()){
			//System.out.println(scanner.nextLine());
			Matcher matcher = pattern.matcher(scanner.nextLine());
			if(matcher.find()){
				Integer[] inputs = new Integer[6];
				inputs[0] = Integer.parseInt(matcher.group(1));
				inputs[1] = Integer.parseInt(matcher.group(2));
				inputs[2] = Integer.parseInt(matcher.group(3));
				inputs[3] = Integer.parseInt(matcher.group(4));
				inputs[4] = Integer.parseInt(matcher.group(5));
				inputs[5] = Integer.parseInt(matcher.group(6));
				int output = Integer.parseInt(matcher.group(7));
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
			Integer inputs[] = new Integer[6];
			inputs[j++] = genes[i++];
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
		if(inputData == null){
			importRules();
		}
		int fitness = 0;
		ArrayList<Data<Integer>> generatedRules = parseGenes();
		for(Data<Integer> input : inputData){
			for(Data<Integer> generatedRule : generatedRules){
				if(generatedRule.matches(input)){
					if(generatedRule.getOutput() == input.getOutput()){
						fitness ++;
					}
					break;
				}
			}
		}
		return fitness;
	}
	
	@Override
	public int maxFitness(){
		return 64;
	}

	@Override
	public DS2 copy() {
		DS2 tChrom = new DS2(this.genes);
		return tChrom;
	}
	
	@Override
	public String toString(){
		return Arrays.toString(genes) + " : " + this.getFitness();
	}

	@Override
	public void replaceGenes(DS2 tChrom) {
		int[] newGenes = Arrays.copyOf(tChrom.getGenes(), geneSize);
		this.genes = newGenes;
	}


	@Override
	public int compareTo(DS2 o) {
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
