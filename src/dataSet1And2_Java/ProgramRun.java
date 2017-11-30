/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSet1And2_Java;

/**
 *
 * @author alvinho0304
 */
public class ProgramRun {

    static int genesMax = 1000;
    static int popSize = 200;
    static int sizeOfGenes =9;
    static int sizeOfRules = 7;
    static double mutationRate = 0.01;
    //This is disabled since singlePointCrossOver applied on data1 and data2
    //static double crossoverRate = 0.7;
    
    static String filename = "data2.txt";
    static String pathToCreate = "src/CSV/data2_pop"+ popSize +"_genes" + sizeOfGenes
            + "_mutate" + mutationRate +"_v5.csv";

    public static void main(String[] args) {

        DataManager dm = new DataManager(sizeOfRules);
        //Step1: Read those training data
        int[][] ruleData = dm.readDataFileBoolInts(filename);
        //Step2: Create a new CSV File
        dm.createNewCSV(pathToCreate);

        // Step3:Create an initial population
        Population myPop = new Population(popSize, true, sizeOfGenes, sizeOfRules);
        //Step4: Setup the first generation
        int generationCount = 1;

        //Step5: loop till max number of generations
        while (generationCount <= genesMax) {
            //Step 6: Calculate the fitness function which compared with rules

            myPop.fitnessFunctionCal(ruleData);
            //Debug log
            System.out.println("Generation: " + generationCount + " BestFittest: " + myPop.getbestFitness());
            //Step7: save all bestFitness into CSV Files
            dm.saveBestFitnessToCSV(myPop.getbestFitness() + "");

            //Step8: doing basic GA Programming
            myPop.selection(popSize);
            myPop.crossover(sizeOfGenes, sizeOfRules);
            myPop.mutation(mutationRate);
            myPop.nextGene();

            //Step8: after finished GA programming, deal with the next generation,keep loop till 1000
            generationCount++;
        }
        //Debug log
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Result:");
        myPop.getbestIndividual();

        //Step9: save the contents of the csv
        dm.closeSaveCSV();

    }
}
