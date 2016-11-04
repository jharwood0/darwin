package com.j4_harwood.biocomp.darwin;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
	private static int populationSize = 100;
	private static int numGenerations = 5000;
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //GeneticAlgorithm<CountOnesChrom> GA = new GeneticAlgorithm<CountOnesChrom>(createInitialPopulation());
        //GeneticAlgorithm<DataSet1> GA = new GeneticAlgorithm<DataSet1>(createDS1Population());
        GeneticAlgorithm<DataSet2> GA = new GeneticAlgorithm<DataSet2>(createDS2Population());
        System.out.println("Population size = "+GA.size());
        GA.evolve(numGenerations);
    }
    
    /* Create the initial population */
    public static ArrayList<CountOnesChrom> createInitialPopulation(){
    	ArrayList<CountOnesChrom> tPop = new ArrayList<>();
    	for(int i = 0; i < populationSize; i++){
    		tPop.add(new CountOnesChrom());
    	}
    	return tPop;
    }
    
    /* Create the initial population */
    public static ArrayList<DataSet1> createDS1Population(){
    	ArrayList<DataSet1> tPop = new ArrayList<>();
    	for(int i = 0; i < populationSize; i++){
    		tPop.add(new DataSet1());
    	}
    	return tPop;
    }
    
    /* Create the initial population */
    public static ArrayList<DataSet2> createDS2Population(){
    	ArrayList<DataSet2> tPop = new ArrayList<>();
    	for(int i = 0; i < populationSize; i++){
    		tPop.add(new DataSet2());
    	}
    	return tPop;
    }
}
