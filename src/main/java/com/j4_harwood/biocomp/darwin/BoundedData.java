package com.j4_harwood.biocomp.darwin;

public class BoundedData{
	// this is a single rule
	float input[];
	int output;
	public BoundedData(float input[], int output){
		this.input = input;
		this.output = output;
	}
	public boolean fits(float testInput[]){
		//for ever testInput i there are two local input
		//System.out.println("generated: "+Arrays.toString(input));
		//System.out.println("test: "+ Arrays.toString(testInput));
		int i = 0;
		for(int j = 0; j < testInput.length; j++){
			int tlow = i;
			i++;
			int thigh = i;
			//System.out.println("high: "+input[thigh]+ ": low : "+input[tlow]+" actual : "+ testInput[j]);
			if(!(testInput[j] <= input[thigh] && testInput[j] >= input[tlow])){
				return false;
			}
			i++;
		}
		return true;
	}
	public int getOutput(){
		return output;
	}
	@Override
	public String toString(){
		String o = "";
		for(float i : this.input){
				o += i+" ";
		}
		return o + " : "+output;
	}
}
