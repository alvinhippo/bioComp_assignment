/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSet3_Java;

import java.util.Random;

/**
 *
 * @author alvinho0304
 */
public class Individual {

    private float[][] genes;
    private int genesLength;
    private int conditionLength;
    private int outputLength;
    private int totalLength;
    // Cache - could say that is an output too
    private int fitness;

    //public Individual() {
    //}

    public Individual(int genesL, int condL, int outL) {
        this.genesLength = genesL;
        this.conditionLength = condL;
        this.outputLength = outL;
        this.totalLength = (conditionLength * 2) + outputLength;
        this.fitness = 0;
        this.genes = new float[genesLength][totalLength];
        randGenes();
        organiseBounds();
    }

    public Individual(Individual copy) {
        //Copy when the new gene need to recover old gene
        this.genesLength = copy.getGenesLength();
        this.totalLength = copy.getTotalLength();
        float[][] tempGenes = new float[this.genesLength][this.totalLength];

        for (int i = 0; i < tempGenes.length; i++) {
            for (int j = 0; j < tempGenes[i].length; j++) {
                tempGenes[i][j] = copy.getRules()[i][j];
            }
        }
        //Copy all the stuffs as well
        this.genes = tempGenes;
        this.genesLength = copy.getGenesLength();
        this.conditionLength = copy.getConditionLength();
        this.outputLength = copy.getOutputLength();
        this.fitness = copy.getFitness();
    }

    public void randGenes() {
        Random rand = new Random();
        for (float[] gene : genes) {
            //randomise conditions
            for (int j = 0; j < gene.length; j++) {
                if (j == genes.length - 1) {
                    gene[j] = rand.nextInt(2);
                } else {
                    gene[j] = (float) Math.random();
                }
            }
        }
    }

    public void organiseBounds() {
        for (float[] gene : genes) {
            //loop the each gene
            for (int j = 0; j < (gene.length - outputLength) / 2; j++) {
                //loop through conditions and check bounds
                int offset = j * 2;
                float lowerBound = Math.min(gene[offset], gene[offset + 1]);
                float upperBound = Math.max(gene[offset], gene[offset + 1]);
                gene[offset] = lowerBound;
                gene[offset + 1] = upperBound;
            }
        }
    }

    public void mutationCreepConditions(double mutationRate, double omegaOff) {
        for (float[] gene : genes) {
            //loop through each gene
            for (int j = 0; j < conditionLength; j++) {
                //loop through each condition bound pair (only)
                if (mutationRate > Math.random()) {
                    int offset = j * 2;
                    float lowerBound = gene[offset];
                    float upperBound = gene[offset + 1];
                    lowerBound += Math.random() > 0.5 ? Math.random() * omegaOff : -Math.random() * omegaOff;
                    upperBound += Math.random() > 0.5 ? Math.random() * omegaOff : -Math.random() * omegaOff;
                    lowerBound = endBound(lowerBound);
                    upperBound = endBound(upperBound);
                    gene[offset] = Math.min(lowerBound, upperBound);
                    gene[offset + 1] = Math.max(lowerBound, upperBound);
                }
            }
        }
    }
    
        public void mutationOutput(double MUTATION_RATE) {
        for (int i = 0; i < genes.length; i++) {
            if (MUTATION_RATE > Math.random()) {
                genes[i][genes[i].length - 1] = (int) genes[i][genes[i].length - 1] ^ 1;
            }
        }
    }
    
        private float endBound(float newValue) {
        if (newValue < 0) {
            return 0;
        }
        if (newValue > 1) {
            return 1;
        } else {
            return newValue;
        }
    }

//    public void mutation(double mutationRate) {
//        Random rand = new Random();
//        for (float[] gene : genes) {
//            for (int j = 0; j < gene.length - 1; j++) {
//                if (mutationRate > Math.random()) {
//                    gene[j] = rand.nextInt(3);
//                }
//            }
//            if (mutationRate > Math.random()) {
//                gene[gene.length - 1] = rand.nextInt(2);
//            }
//        }
//    }


    /* Getters and setters */
    public float[][] getRules() {
        return genes;
    }

    public void setRules(float[][] genes) {
        this.genes = genes;
    }
    
        public float[] getGenes(int index) {
        return genes[index];
    }

    public float[][] getGenes() {
        return genes;
    }

    public void setGeneFromIndex(int index, int index2, float value) {
        this.genes[index][index2] = value;
    }

    public void setGenes(int index, float[] gene) {
        this.genes[index] = gene;
    }

    public int getGenesLength() {
        return genesLength;
    }

    public void setGenesLength(int genesL) {
        this.genesLength = genesL;
    }

    public int getConditionLength() {
        return conditionLength;
    }

    public void setConditionLength(int condL) {
        this.conditionLength = condL;
    }

    public int getOutputLength() {
        return outputLength;
    }

    public void setOutputLength(int outL) {
        this.outputLength = outL;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
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

    public void Evaluation(float[][] dataSet) {
        int tempFitness = 0;
        for (float[] fs : dataSet) {
            //loop through rules
            for (float[] gene : genes) {
                //loop through each gene
                //eval conditions
                boolean MatchedCond = true;
                for (int k = 0; k < fs.length - outputLength; k++) {
                    //loop through each part of rule
                    float value = fs[k];
                    //offset--> [j][k*2]
                    int offset = k * 2;
                    float lowerBound = gene[offset];
                    float upperBound = gene[offset + 1];
                    if (!(lowerBound <= value && value <= upperBound)) {
                        MatchedCond = false;
                        break;
                    }
                }
                //eval all conditions and output
                if (MatchedCond) {
                    if (gene[this.totalLength - 1] == fs[fs.length - 1]) {
                        tempFitness++;
                    }
                    break; //go to next rule to eval
                }
            }
        }

        //set individuals new fitness
        this.fitness = tempFitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < sizeOfGenes(); i++) {
            geneString += getGenes();
        }
        return geneString;
    }
}
