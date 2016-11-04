package com.j4_harwood.biocomp.darwin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DS3 implements Chromosome, Comparable<DS3>{
	/* to encapsulate a rule */
	
	private final int numRules = 10;
	private final int geneSize = ((6*2)+1)*numRules;
	
	private static ArrayList<Data<Float>> inputData;
	
	private float[] genes = new float[geneSize];
	private int fitness;
	public DS3(){
		this.initialise();
	}
	
	private void initialise(){
		for(int i = 0; i < geneSize; i++){
			genes[i] = newGene();
		}
		calculateFitness();
	}
	
	private float newGene(){
		Double toBeTruncated = GARand.nextDouble();

		Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
		    .setScale(6, RoundingMode.HALF_UP)
		    .doubleValue();
		return truncatedDouble.floatValue();
	}
	
	public DS3(float[] newGenes){
		genes = Arrays.copyOf(newGenes, newGenes.length);
		calculateFitness();
	}

	@Override
	public void mutate(double mutationRate) {
		for(int i = 0; i < geneSize; i++){
			double rnd = GARand.nextDouble();
			if(rnd < mutationRate){
				float prevGene = genes[i];
				while(prevGene == genes[i]){
					genes[i] = newGene();
				}
			}
		}
		calculateFitness();
	}
	

	@Override
	public DS3[] crossover(Chromosome parent1, Chromosome parent2) {
		//Creates 2 NEW children
		float[] p1 = ((DS3)parent1).getGenes();
		float[] p2 = ((DS3)parent2).getGenes();
		
		float[] c1 = new float[geneSize];
		float[] c2 = new float[geneSize];
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
		DS3[] children = new DS3[2];
		children[0] = new DS3(c1);
		children[1] = new DS3(c2);
		return children;
	}

	public float[] getGenes() {
		return genes;
	}

	@Override
	public int size() {
		return geneSize;
	}
	
	private void importRules(){
		inputData = new ArrayList<Data<Float>>();
		Scanner scanner = new Scanner(getClass().getResourceAsStream("DS3.txt"));
		String p = "([-+]?[0-9]*\\.?[0-9]*)\\s([-+]?[0-9]*\\.?[0-9]*)\\s([-+]?[0-9]*\\.?[0-9]*)\\s([-+]?[0-9]*\\.?[0-9]*)\\s([-+]?[0-9]*\\.?[0-9]*)\\s([-+]?[0-9]*\\.?[0-9]*)\\s(\\d)";
		Pattern pattern = Pattern.compile(p);
		while(scanner.hasNextLine()){
			//System.out.println(scanner.nextLine());
			Matcher matcher = pattern.matcher(scanner.nextLine());
			if(matcher.find()){
				Float[] inputs = new Float[6];
				inputs[0] = Float.parseFloat(matcher.group(1));
				inputs[1] = Float.parseFloat(matcher.group(2));
				inputs[2] = Float.parseFloat(matcher.group(3));
				inputs[3] = Float.parseFloat(matcher.group(4));
				inputs[4] = Float.parseFloat(matcher.group(5));
				inputs[5] = Float.parseFloat(matcher.group(6));
				int output = Integer.parseInt(matcher.group(7));
				Data<Float> tData = new Data<Float>(inputs, output);
				inputData.add(tData);
			}
		}
		scanner.close();
	}
	
	private ArrayList<BoundedData> parseGenes(){
		ArrayList<BoundedData> generatedRules = new ArrayList<BoundedData>();

		int i = 0;
		while(i < geneSize){
			Float input[] = new Float[6*2];
			for(int j = 0; j < input.length; j++){
				input[j] = genes[i];
				i++;
			}
			int output = Math.round(genes[i]); //CHECK THIS, should round
			i++;
			generatedRules.add(new BoundedData(input, output));
		}
		
		return generatedRules;
	}
	
	@Override
	public int getFitness(){
		return fitness;
	}

	private void calculateFitness() {
		if(inputData == null){
			importRules();
		}
		int fitnewFitness = 0;
		ArrayList<BoundedData> generatedRules = parseGenes();
		for(Data<Float> input : inputData){
			for(BoundedData generatedRule : generatedRules){
				if(generatedRule.fits(input.getInputs())){
					if(generatedRule.getOutput() == input.getOutput()){
						fitnewFitness ++;
					}
					break;
				}
			}
		}
		fitness = fitnewFitness;
	}
	
	@Override
	public int maxFitness(){
		return 1200;
	}

	@Override
	public DS3 copy() {
		DS3 tChrom = new DS3(this.genes);
		return tChrom;
	}
	
	@Override
	public String toString(){
		return Arrays.toString(genes) + " : " + this.getFitness();
	}

	@Override
	public void replaceGenes(Chromosome tChrom) {
		float[] newGenes = Arrays.copyOf(((DS3)tChrom).getGenes(), geneSize);
		this.genes = newGenes;
		calculateFitness();
	}


	@Override
	public int compareTo(DS3 o) {
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
