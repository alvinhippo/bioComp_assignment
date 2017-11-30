/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSet3_Java;

/**
 *
 * @author alvinho0304
 */
public class ProgramRun {

    static int genesMax = 3000;
    static int popSize = 200;
    static int sizeChromos = 10;
    static int sizeCondL = 6;
    static int outL = 1;
    static double mutationRate = 0.001;
    static double crossoverRate = 0.9;
    static double omegaOff = 0.3;

    static String filename = "data3.txt";
    static String pathToCreateGP = "src/CSV/data3_pop" + popSize + "_genes" + sizeChromos
            + "_mutate" + mutationRate + "_crossover" + crossoverRate + "_v5_GP.csv";

    static String pathToCreateIndiv = "src/CSV/data3_pop" + popSize + "_genes" + sizeChromos
            + "_mutate" + mutationRate + "_crossover" + crossoverRate + "_v5_INDIV.csv";

    public static void main(String[] args) {

        DataManager dm = new DataManager(sizeCondL);
        //Step1: Read those training data
        dm.readDataFileBoolInts(filename);
        //Step2: Create a new CSV File
        dm.createNewCSVByGP(pathToCreateGP);
        dm.createNewCSVByIndiv(pathToCreateIndiv);
        dm.createDataSets();

        // Step3:Create an initial population
        Population myPop = new Population(popSize, true, sizeChromos, sizeCondL, outL);
        //Step4: Setup the first generation
        int generationCount = 2;
        //float[][] testS = dm.dSets;
        //Step5: loop till max number of generations
        while (generationCount <= genesMax) {

            if (generationCount % 10 == 0) {
                dm.dSets = dm.testSet;
                System.out.println("*******************************");
            } else {
                dm.dSets = dm.trainSet;
            }
            //Step 6: Calculate the fitness function which compared with rules
            for (Individual popParent : myPop.popParent) {
                popParent.Evaluation(dm.dSets);
            }
            //Step8: doing basic GA Programming
            myPop.evolveMyPop(crossoverRate, mutationRate, omegaOff);

            //Debug log
            System.out.println("Generation: " + generationCount + " BestFitness: " + myPop.bestIndiv.getFitness());
            //Step7: save all bestFitness into CSV Files

            dm.saveBestFitnessToCSVByGP("" + myPop.bestIndiv.getFitness());
            dm.saveBestFitnessToCSVByIndiv("" + myPop.bestIndiv.getFitness());

            //Step8: after finished GA programming, copy the child to parent
            myPop.copyChildToPar();
            generationCount++;
        }
        //Debug log
        System.out.println("Solution found!");

        //Step9: save the contents of the csv
        dm.closeSaveCSVByGP();
        dm.closeSaveCSVByIndiv();
    }
}
