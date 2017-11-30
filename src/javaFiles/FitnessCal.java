/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFiles;

/**
 *
 * @author alvinho0304
 */
public class FitnessCal {

    static int numberOfGenerations = 1000;
    static int NumberOfRule = 10;
    static int[] solution = new int[64];


    /* Public methods */
    // Set a candidate solution as a int array
    public static void setSolution(int[] newSolution) {
        solution = newSolution;
    }

    // To make it easier we can use this method to set our candidate solution 
    // with string of 0s and 1s
    static void setSolution(String newSolution) {
        solution = new int[newSolution.length()];
        // Loop through each character of our string and save it in our int 
        // array
        for (int i = 0; i < newSolution.length(); i++) {
            String character = newSolution.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                solution[i] = Byte.parseByte(character);
            } else {
                solution[i] = 0;
            }
        }
    }

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static int getFitness(Individual indiv, int[][] ruleData) {
        int fitness = 0;
        
        //Creat a bool to check condition status
        boolean matchedCond;
        
        // Loop through training data
        for (int[] ruleBase : ruleData) {
                
            //Don't know whether it is correct or not
            for (int[] cond : indiv.getRules()) {
                matchedCond  = true;
                
                //check conditions and loop through each gene
                for (int k = 0; k < cond.length -1; k++) {
                    if(cond[k] == ruleBase[k] || cond[k] == 2) {
                        matchedCond = true;
                    } else {
                        matchedCond = false;
                        break;
                    }
                }
                
                //check if all conditions matched or not
                if(matchedCond) {
                    //if output matches, fitness ++
                    if(cond[cond.length -1] == ruleBase[ruleBase.length -1]) {
                        fitness++;
                    }
                    break; 
                }
            }
        }
        
        return fitness;
    }
    
    // Get optimum fitness
    static int getMaxFitness() {
        int maxFitness = solution.length;
        return maxFitness;
    }
}
