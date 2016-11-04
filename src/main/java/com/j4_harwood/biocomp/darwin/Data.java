package com.j4_harwood.biocomp.darwin;

import java.util.Arrays;

public class Data<K extends Number>{
		K[] inputs;
		int output;
		public Data(K[] inputs, int output){
			this.inputs = inputs;
			this.output = output;
		}
		public boolean matches(Data<K> input){
			for(int i = 0; i < inputs.length; i++){
				if((inputs[i].equals(input.getInputs()[i]) == false) && (inputs[i].equals(new Integer(2)) == false) && (input.getInputs().equals(new Integer(2)) == false)){
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
		public K[] getInputs(){
			return inputs;
		}
	}
	