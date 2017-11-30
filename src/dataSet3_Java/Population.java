/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSet3_Java;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author alvinho0304
 */
public class Population {

    public Individual[] popParent;
    public Individual[] popChild;
    public Individual bestIndiv;

    private int bestFitness;

    //static methods
    static Individual indiv;

    /*
     * Constructors
     */
    Individual[] individuals;

    // Create a population
    public Population(int populationSize, boolean initialise, int sizeChromo, int sizeCondL, int outL) {
        popParent = new Individual[populationSize];
        popChild = new Individual[populationSize];

        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < populationSize; i++) {
                popParent[i] = new Individual(sizeChromo, sizeCondL, outL);
                popChild[i] = new Individual(sizeChromo, sizeCondL, outL);

            }
            bestIndiv = new Individual(sizeChromo, sizeCondL, outL);
        }
    }
    public int getbestFitness() {
        return bestFitness;
    }

    public void evolveMyPop(double crossoverRate, double mutationRate, double omega) {
        for (int i = 0; i < popChild.length; i++) {
            //Selection part
            Individual[] par = new Individual[2];
            par[0] = tornamentSelection(popParent);
            par[1] = tornamentSelection(popParent);

            //Crossover
            Individual[] children = singlePointCrossover(par[0], par[1], crossoverRate);

            //Mutation
            children[0].mutationCreepConditions(mutationRate, omega);
            children[0].mutationOutput(mutationRate);
            popChild[i] = children[0];

            if (i + 1 < popChild.length) {
                i++;
                children[1].mutationCreepConditions(mutationRate, omega);
                children[1].mutationOutput(mutationRate);
                popChild[i] = children[1];

            }
        }
        getBestIndividual();
    }

    public Individual[] singlePointCrossover(Individual indiv1, Individual indiv2, double crossoverRate) {
        Individual[] children = new Individual[2];

        if (crossoverRate > Math.random()) {
            int crossingPoint = new Random().nextInt(indiv1.getGenes().length * indiv1.getGenes()[0].length);
            int pointer = 0;
            int i = 0;
            while (crossingPoint > pointer) {
                int m = pointer % indiv1.getGenes()[0].length;

                if (m == indiv1.getGenes()[0].length - 1) {
                    //reached end of a rule, so i++;
                    i++;
                }

                //swap
                float temp = indiv1.getGenes()[i][m];
                indiv1.setGeneFromIndex(i, m, indiv2.getGenes()[i][m]);
                indiv2.setGeneFromIndex(i, m, temp);

                pointer++;
            }
        }
        children[0] = new Individual(indiv1);
        children[1] = new Individual(indiv2);

        return children;
    }

    private Individual tornamentSelection(Individual[] parentPop) {
        Random rand = new Random();
        Individual mutate1 = new Individual(popParent[rand.nextInt(parentPop.length)]);
        Individual mutate2 = new Individual(popParent[rand.nextInt(parentPop.length)]);
        if (mutate1.getFitness() > mutate2.getFitness()) {
            return new Individual(mutate1);
        } else {
            return new Individual(mutate2);
        }
    }

    public void getBestIndividual() {
        int bestFit = 0;
        for (int i = 1; i < popChild.length; i++) {
            if (popChild[i].getFitness() > popChild[bestFit].getFitness()) {
                bestFit = i;
            }
        }
        bestIndiv = new Individual(popChild[bestFit]);
       
    }

    public void copyChildToPar() {
        for (int i = 0; i < popParent.length; i++) {
            popParent[i] = new Individual(popChild[i]);
        }
    }
}
