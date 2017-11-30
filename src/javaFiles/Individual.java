/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFiles;

import java.util.Random;

/**
 *
 * @author alvinho0304
 */
public class Individual {

    private int[][] genes;
    // Cache - could say that is an output too
    private int fitness = 0;

    public Individual() {
    }

    public Individual(int sizeOfGenes, int sizeOfRules) {
        genes = new int[sizeOfGenes][sizeOfRules];
    }

    public Individual(Individual copy) {
        //Copy when the new gene need to recover old gene
        int[][] tempGenes = copy.getRules();
        genes = new int[tempGenes.length][tempGenes[0].length];
        for (int i = 0; i < tempGenes.length; i++) {
            for (int j = 0; j < tempGenes[i].length; j++) {
                genes[i][j] = tempGenes[i][j];
            }
        }
        //Copy the fitness as well
        fitness = copy.getFitness();
    }
    
    public void randGenes() {
        Random rand = new Random();
        for (int[] gene : genes) {
            //randomise conditions
            for (int j = 0; j < gene.length - 1; j++) {
                gene[j] = rand.nextInt(3);
            }
            //randomise output
            gene[gene.length - 1] = rand.nextInt(2);
        }
    }

    public void mutation(double mutationRate) {
        Random rand = new Random();
        for (int[] gene : genes) {
            for (int j = 0; j < gene.length - 1; j++) {
                if (mutationRate > Math.random()) {
                    gene[j] = rand.nextInt(3);
                }
            }
            if (mutationRate > Math.random()) {
                gene[gene.length - 1] = rand.nextInt(2);
            }
        }
    }


    /* Getters and setters */

    public int[][] getRules() {
        return genes;
    }

    public void setRules(int[][] genes) {
        this.genes = genes;
    }

    public int[] getGenes(int index) {
        return genes[index];
    }

    public void setGenes(int index, int[] gene) {
        this.genes[index] = gene;
    }

    /* Public methods */
    public int sizeOfGenes() {
        return genes.length;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < sizeOfGenes(); i++) {
            geneString += getGenes(i);
        }
        return geneString;
    }
}
