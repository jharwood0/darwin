package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SPECTTestEngine {
	ArrayList<Data<Integer>> generatedRules = new ArrayList<Data<Integer>>();
	ArrayList<Data<Integer>> testData = new ArrayList<Data<Integer>>();
	public SPECTTestEngine(){
		
	}
	
	public void setTestSet(String f){
		testData = new ArrayList<Data<Integer>>();
		Scanner scanner = new Scanner(getClass().getResourceAsStream(f));
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
				testData.add(tData);
				System.out.println(tData.toString());
			}
		}
		scanner.close();
	}
	
	public void inputRules(Integer[] genes){
		
		for(int i = 0; i < genes.length;){
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
	}

	public void execute() {
		int fitness = 0;
		for(Data<Integer> test : testData){
			for(Data<Integer> gen : generatedRules){
				if(gen.matches(test)){
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
