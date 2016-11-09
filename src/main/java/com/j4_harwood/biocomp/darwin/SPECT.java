package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SPECT implements Chromosome<SPECT>, Comparable<SPECT>{
	private final int numRules = 9;
	private final int geneSize = (22+1)*numRules;
	private int fitness = 0;
	private static ArrayList<Data<Integer>> inputData;
	
	private Integer[] genes = new Integer[geneSize];
	public SPECT(){
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
	
	public SPECT(Integer[] newGenes){
		genes = Arrays.copyOf(newGenes, newGenes.length);
		calcFitness();
	}

	@Override
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
	public SPECT[] crossover(SPECT parent1, SPECT parent2) {
		//Creates 2 NEW children
		Integer[] p1 = parent1.getGenes();
		Integer[] p2 = parent2.getGenes();
		
		Integer[] c1 = new Integer[geneSize];
		Integer[] c2 = new Integer[geneSize];
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
		SPECT[] children = new SPECT[2];
		children[0] = new SPECT(c1);
		children[1] = new SPECT(c2);
		return children;
	}

	public Integer[] getGenes() {
		return genes;
	}

	@Override
	public int size() {
		return geneSize;
	}
	
	private void importRules(){
		inputData = new ArrayList<Data<Integer>>();
		Scanner scanner = new Scanner(getClass().getResourceAsStream("SPECT.train"));
		String p = "(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d),(\\d)";
		Pattern pattern = Pattern.compile(p);
		while(scanner.hasNextLine()){
			//System.out.println(scanner.nextLine());
			Matcher matcher = pattern.matcher(scanner.nextLine());
			if(matcher.find()){
				Integer[] inputs = new Integer[22];
				int output = 0;
				for(int i = 0; i < 23; i++){
					if(i == 0){
						output = Integer.parseInt(matcher.group(1));
					}else{
						inputs[i-1] = Integer.parseInt(matcher.group(i+1));
					}
				}
				
				Data<Integer> tData = new Data<Integer>(inputs, output);
				inputData.add(tData);
				System.out.println(tData.toString());
			}
		}
		scanner.close();
	}
	
	private ArrayList<Data<Integer>> parseGenes(){
		ArrayList<Data<Integer>> generatedRules = new ArrayList<Data<Integer>>();
		for(int i = 0; i < geneSize;){
			int j = 0;
			Integer inputs[] = new Integer[22];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
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
		return 80;
	}

	@Override
	public SPECT copy() {
		SPECT tChrom = new SPECT(this.genes);
		return tChrom;
	}
	
	@Override
	public String toString(){
		return Arrays.toString(genes) + " : " + this.getFitness();
	}

	@Override
	public void replaceGenes(SPECT tChrom) {
		Integer[] newGenes = Arrays.copyOf(tChrom.getGenes(), geneSize);
		this.genes = newGenes;
		calcFitness();
	}


	@Override
	public int compareTo(SPECT o) {
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
