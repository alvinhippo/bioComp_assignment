/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSet1And2_Java;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author alvinho0304
 */
public class Population {


    public static Individual[] pop;
    public static Individual[] mPool;

    private int bestFitness;

    //static methods
    static Individual indiv;
    
    /*
     * Constructors
     */
    Individual[] individuals;

    // Create a population
    public Population(int populationSize, boolean initialise, int sizeOfGenes, int sizeOfRules) {
        pop = new Individual[populationSize];
        mPool = new Individual[populationSize];

        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < pop.length; i++) {
                pop[i] = new Individual(sizeOfGenes, sizeOfRules);

            }
            initialisePopulation();
        }
    }

    public void initialisePopulation() {
        for (Individual population : pop) {
            population.randGenes();
        }
    }

    /* Getters */
    
    public void fitnessFunctionCal(int[][] ruleData) {
        bestFitness = 0;

        for (int i = 0; i < pop.length; i++) {
            //calculate and store the fitness back into each person in population
            int fitness = FitnessCal.getFitness(pop[i], ruleData);
            pop[i].setFitness(fitness);
            //used juest to find the best fitness of the pop
            if (bestFitness < pop[i].getFitness()) {
                bestFitness = pop[i].getFitness();
            }
        }
    }

    public int getbestFitness() {
        return bestFitness;
    }

    public void selection(int sizeOfPopulation) {
        for (int i = 0; i < sizeOfPopulation; i++) {
            mPool[i] = tornamentSelection(sizeOfPopulation);
        }
    }


    public void crossover(int sizeOfGenes, int sizeOfRules) {

        for (int i = 0; i < mPool.length / 2; i++) {
            int offset = i * 2;
            Individual[] children = singlePointCrossover(mPool[offset], mPool[offset + 1], sizeOfGenes, sizeOfRules);
            mPool[i] = children[0];
            mPool[i + 1] = children[1];
        }
    }

    public void mutation(double MUTATION_RATE) {
        // Loop through genes
        for (int i = 0; i < mPool.length; i++) {
            mPool[i].mutation(MUTATION_RATE);
        }
    }

    public void nextGene() {
        for (int i = 0; i < pop.length; i++) {
            pop[i] = new Individual(mPool[i]);
        }
    }

    private Individual[] singlePointCrossover(Individual indiv1, Individual indiv2, int sizeOfGenes, int sizeOfRules) {
        Individual[] children = new Individual[2];
        children[0] = new Individual(indiv1);
        children[1] = new Individual(indiv2);

        int crossingPoint = new Random().nextInt(sizeOfGenes * sizeOfRules);
        int pointer = 0;
        for (int i = 0; i < indiv1.getRules().length; i++) {
            for (int j = 0; j < indiv1.getGenes(i).length; j++) {
                if (pointer < crossingPoint) {
                    //swap
                    int temp = children[0].getRules()[i][j];
                    children[0].getRules()[i][j] = children[1].getRules()[i][j];
                    children[1].getRules()[i][j] = temp;
                } else {
                    break;
                }
                pointer++;
            }
            if (pointer >= crossingPoint) {
                break;
            }
        }

        return children;
    }

    private Individual tornamentSelection(int sizeOfPopulation) {
        Random rand = new Random();
        Individual mutate1 = new Individual(pop[rand.nextInt(sizeOfPopulation)]);
        Individual mutate2 = new Individual(pop[rand.nextInt(sizeOfPopulation)]);
        if (mutate1.getFitness() > mutate2.getFitness()) {
            return mutate1;
        } else {
            return mutate2;
        }
    }

    public void getbestIndividual() {
        Individual indivB = pop[0];
        for (int i = 1; i < pop.length; i++) {
            if (pop[i].getFitness() > indivB.getFitness()) {
                indivB = pop[i];
            }
        }

        System.out.println("Best Individuals:");
        for (int i = 0; i < indivB.getRules().length; i++) {
            System.out.println(Arrays.toString(indivB.getGenes(i)));
        }
    }
}
