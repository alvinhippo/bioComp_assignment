/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvinho0304
 */
public class DataManager {


    //global variables
    private int ruleSize; // Rule size = conditions length + output length
    static int Vars = 5;
    private final int[] variables;
    public int dclass;


    File f;
    FileWriter fw;

    public DataManager(int ruleSize) {
        this.ruleSize = ruleSize;

        this.variables = new int[Vars];
    }

    public int[][] readDataFileBoolInts(String fileName) {
        int[][] rules;
        ArrayList<Integer[]> rulesAL = new ArrayList<>();

        Scanner scan = new Scanner(DataManager.class.getResourceAsStream("/testData/" + fileName));
        //remove first unusable line
        scan.nextLine();

        while (scan.hasNextLine()) {
            Integer[] newRule;

            String parts = scan.nextLine().replace(" ", "");

            //store everything as an int array
            newRule = new Integer[parts.length()];
            for (int i = 0; i < newRule.length; i++) {
                newRule[i] = Integer.parseInt("" + parts.charAt(i));
            }

            rulesAL.add(newRule);
        }

        //convert the arraylist to int[][]
        rules = new int[rulesAL.size()][rulesAL.get(0).length];
        for (int i = 0; i < rulesAL.size(); i++) {
            for (int j = 0; j < rulesAL.get(i).length; j++) {
                rules[i][j] = rulesAL.get(i)[j];
            }
        }

        //output just to check
        for (int i = 0; i < rules.length; i++) {
            System.out.println(Arrays.toString(rules[i]));
        }

        return rules;
    }

    public int getVar(int index) {
        return variables[index];
    }

    public void setCond(int index, int value) {
        variables[index] = value;
    }

    /*Public methods*/
    public int size() {
        return variables.length;
    }

    public void setDclass(int index) {
        dclass = index;
    }

    /* CSV Part */
    public void createNewCSV(String pathToCreate) {
        f = new File(pathToCreate);
        try {
            if (!f.exists()) {
                f.createNewFile();
            } else {
                f.delete();
                f.createNewFile();
            }
            fw = new FileWriter(f);
            fw.append("Fitness");
            fw.append('\n');
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void saveBestFitnessToCSV(String bestFitness) {
        try {
            fw.append(bestFitness);
            fw.append("\n");
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public void closeSaveCSV() {
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //main method just used for testing
//    public static void main(String[] args) {
//        DataManager dm = new DataManager(6);
//        
//        //reading file works fine!
//        Rule[] rules = dm.readDataFileBool("data2.txt");
//        System.out.println("=========================");
//        //both methods work fine!
//        int[][] rules2 = dm.readDataFileBoolInts("data2.txt");
//    }

}
