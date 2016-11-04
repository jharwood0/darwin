package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
	private static int populationSize = 100;
	private static int numGenerations = 3000;
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //GeneticAlgorithm<CountOnesChrom> GA = new GeneticAlgorithm<CountOnesChrom>(createInitialPopulation());
        //GeneticAlgorithm<DataSet2> GA = new GeneticAlgorithm<DataSet2>(createDS2Population());
        GeneticAlgorithm<DS3> GA = new GeneticAlgorithm<DS3>(createDS3Population());
        //System.out.println("Population size = "+GA.size());
        GA.evolve(numGenerations);
        
        //We have evolved
        TestEngine TE = new TestEngine();
        TE.setTestSet("data3");
        TE.inputRules(GA.getFittest().getGenes());
        TE.execute();
    }
    

    
    /* Create the initial population */
    public static ArrayList<DataSet2> createDS2Population(){
    	ArrayList<DataSet2> tPop = new ArrayList<>();
    	for(int i = 0; i < populationSize; i++){
    		tPop.add(new DataSet2());
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
}
