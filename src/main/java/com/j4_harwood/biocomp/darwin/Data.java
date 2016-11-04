package com.j4_harwood.biocomp.darwin;

import java.util.Arrays;

public class Data{
		float[] inputs;
		int output;
		public Data(float[] inputs, int output){
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
		public float getOutput() {
			return output;
		}
		public float[] getInputs(){
			return inputs;
		}
	}
	