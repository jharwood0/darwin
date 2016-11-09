package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;

public class App 
{
	private static int populationSize = 100;
	private static int numGenerations = 10000;
    public static void main( String[] args )
    {
        GeneticAlgorithm<DS1> GA1 = new GeneticAlgorithm<DS1>(createDS1Population());
        GeneticAlgorithm<DS2> GA2 = new GeneticAlgorithm<DS2>(createDS2Population());
        GeneticAlgorithm<DS3> GA3 = new GeneticAlgorithm<DS3>(createDS3Population());
        GeneticAlgorithm<SPECT> GA4 = new GeneticAlgorithm<SPECT>(createSPECTPopulation());
        //System.out.println("Population size = "+GA.size());
        GA3.evolve(numGenerations);
        
        /*SPECTTestEngine TE = new SPECTTestEngine();
        TE.setTestSet("SPECT.test");
        TE.inputRules(GA4.getFittest().getGenes());
        TE.execute();*/
        
        //We have evolved
        TestEngine TE = new TestEngine();
        TE.setTestSet("data3");
        TE.inputRules(GA3.getFittest().getGenes());
        TE.execute();
    }
    
    /* Create the initial population */
    public static ArrayList<DS1> createDS1Population(){
    	ArrayList<DS1> tPop = new ArrayList<>();
    	for(int i = 0; i < populationSize; i++){
    		tPop.add(new DS1());
    	}
    	return tPop;
    }
    
    /* Create the initial population */
    public static ArrayList<DS2> createDS2Population(){
    	ArrayList<DS2> tPop = new ArrayList<>();
    	for(int i = 0; i < populationSize; i++){
    		tPop.add(new DS2());
    	}
    	return tPop;
    }
    
    /* Create the initial population */
    public static ArrayList<DS3> createDS3Population(){
    	ArrayList<DS3> tPop = new ArrayList<>();
    	for(int i = 0; i < populationSize; i++){
    		tPop.add(new DS3());
    	}
    	return tPop;
    }
    
    /* Create the initial population */
    public static ArrayList<SPECT> createSPECTPopulation(){
    	ArrayList<SPECT> tPop = new ArrayList<>();
    	for(int i = 0; i < populationSize; i++){
    		tPop.add(new SPECT());
    	}
    	return tPop;
    }
}
