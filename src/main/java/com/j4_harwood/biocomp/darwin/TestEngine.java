package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestEngine {
	ArrayList<BoundedData> generatedRules = new ArrayList<BoundedData>();
	ArrayList<Data<Float>> testData = new ArrayList<Data<Float>>();
	public TestEngine(){
		
	}
	
	public void setTestSet(String f){
		testData = new ArrayList<Data<Float>>();
		Scanner scanner = new Scanner(getClass().getResourceAsStream(f));
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
				testData.add(tData);
			}
		}
		scanner.close();
	}
	
	public void inputRules(float[] genes){
		
		int i = 0;
		while(i < genes.length){
			Float input[] = new Float[6*2];
			for(int j = 0; j < input.length; j++){
				input[j] = genes[i];
				i++;
			}
			int output = Math.round(genes[i]); //CHECK THIS, should round
			i++;
			generatedRules.add(new BoundedData(input, output));
		}
	}

	public void execute() {
		int fitness = 0;
		for(Data<Float> test : testData){
			for(BoundedData gen : generatedRules){
				if(gen.fits(test.getInputs())){
					if(gen.getOutput() == test.getOutput()){
						fitness++;
					}
					break;
				}
			}
		}
		System.out.println("Solved : "+fitness+ " datas!");
	}
}
