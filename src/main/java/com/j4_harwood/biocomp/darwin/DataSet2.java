package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataSet2 implements Chromosome, Comparable<DataSet2>{
	/* to encapsulate a rule */
	class Data{
		int[] inputs;
		int output;
		public Data(int[] inputs, int output){
			if(inputs.length != 6){
				System.out.println("Error! inputs should be 6");
			}else{
				this.inputs = inputs;
				this.output = output;
			}
		}
		public boolean matches(Data input){
			for(int i = 0; i < inputs.length; i++){
				if(inputs[i] != input.getInputs()[i] && inputs[i] != 2 && input.getInputs()[i] != 2){
					return false;
				}
			}
			return true;
		}
		@Override
		public String toString(){
			return Arrays.toString(inputs) + " : " + output;
		}
		public int getOutput() {
			return output;
		}
		public int[] getInputs(){
			return inputs;
		}
	}
	
	private final int numRules = 7;
	private final int geneSize = 7*numRules;
	
	private static ArrayList<Data> inputData;
	
	private int[] genes = new int[geneSize];
	public DataSet2(){
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
	
	public DataSet2(int[] newGenes){
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
	public DataSet2[] crossover(Chromosome parent1, Chromosome parent2) {
		//Creates 2 NEW children
		int[] p1 = ((DataSet2)parent1).getGenes();
		int[] p2 = ((DataSet2)parent2).getGenes();
		
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
		DataSet2[] children = new DataSet2[2];
		children[0] = new DataSet2(c1);
		children[1] = new DataSet2(c2);
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
		inputData = new ArrayList<Data>();
		Scanner scanner = new Scanner(getClass().getResourceAsStream("data2"));
		String p = "(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)\\s(\\d)";
		Pattern pattern = Pattern.compile(p);
		while(scanner.hasNextLine()){
			//System.out.println(scanner.nextLine());
			Matcher matcher = pattern.matcher(scanner.nextLine());
			if(matcher.find()){
				int[] inputs = new int[6];
				inputs[0] = Integer.parseInt(matcher.group(1));
				inputs[1] = Integer.parseInt(matcher.group(2));
				inputs[2] = Integer.parseInt(matcher.group(3));
				inputs[3] = Integer.parseInt(matcher.group(4));
				inputs[4] = Integer.parseInt(matcher.group(5));
				inputs[5] = Integer.parseInt(matcher.group(6));
				int output = Integer.parseInt(matcher.group(7));
				Data tData = new Data(inputs, output);
				inputData.add(tData);
			}
		}
		scanner.close();
	}
	
	private ArrayList<Data> parseGenes(){
		ArrayList<Data> generatedRules = new ArrayList<Data>();
		for(int i = 0; i < geneSize;){
			int j = 0;
			int inputs[] = new int[6];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			inputs[j++] = genes[i++];
			int output = genes[i++];
			Data tData = new Data(inputs, output);
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
		ArrayList<Data> generatedRules = parseGenes();
		for(Data input : inputData){
			for(Data generatedRule : generatedRules){
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
	public DataSet2 copy() {
		DataSet2 tChrom = new DataSet2(this.genes);
		return tChrom;
	}
	
	@Override
	public String toString(){
		return Arrays.toString(genes) + " : " + this.getFitness();
	}

	@Override
	public void replaceGenes(Chromosome tChrom) {
		int[] newGenes = Arrays.copyOf(((DataSet2)tChrom).getGenes(), geneSize);
		this.genes = newGenes;
	}


	@Override
	public int compareTo(DataSet2 o) {
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
