/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSet3_Java;

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
    static int Vars = 5;

    public int dclass;

    public float[][] dSets;
    public float[][] trainSet;
    public float[][] testSet;

    private int ruleSize; // Rule size = conditions length + output length
    private final int[] variables;
    private int CondL;
    private int out;
    private int count = 0;
    File f;
    FileWriter fw;
    FileWriter fw2;

    public DataManager(int ruleSize) {
        this.ruleSize = ruleSize;

        this.variables = new int[Vars];
    }

    public void readDataFileBoolInts(String fileName) {

        ArrayList<Float[]> dataAL = new ArrayList<>();
        int condLTemp = 0;
        int outTemp = 0;

        Scanner scan = new Scanner(DataManager.class.getResourceAsStream("/testData/" + fileName));
        //remove first unusable line
        scan.nextLine();

        while (scan.hasNextLine()) {
            //Integer[] newRule;
            String ruleS = scan.nextLine();
            String[] splits = ruleS.split(" ");

            //save length of condition
            if (condLTemp == 0) {
                //output length is always 1, condition length is the rest.
                condLTemp = splits.length - 1;
            }
            if (outTemp == 0) {
                outTemp = 1;
            }
            //store everything as a set
            Float[] dataSet = new Float[condLTemp + outTemp];
            for (int i = 0; i < dataSet.length; i++) {
                dataSet[i] = Float.parseFloat(splits[i]);
            }
            dataSet[dataSet.length - 1] = Float.parseFloat(splits[splits.length - 1]);

            dataAL.add(dataSet);
        }

        //convert the arraylist to float[][]
        dSets = new float[dataAL.size()][condLTemp + outTemp];
        for (int i = 0; i < dSets.length; i++) {
            for (int j = 0; j < dSets[i].length; j++) {
                dSets[i][j] = dataAL.get(i)[j];
            }
        }
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

    public void createDataSets() {
        trainSet = new float[1000][CondL + out];
        testSet = new float[1000][CondL + out];

        int i = 0;
        while (i < dSets.length / 2) {
            trainSet[i] = dSets[i];
            i++;
        }

        while (i < dSets.length) {
            testSet[i % trainSet.length] = dSets[i];
            i++;
        }
    }

    /* CSV Part */
    public void createNewCSVByGP(String pathToCreate) {
        f = new File(pathToCreate);
        try {
            if (!f.exists()) {
                f.createNewFile();
                System.out.println("new file created");
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

    public void createNewCSVByIndiv(String pathToCreate) {
        f = new File(pathToCreate);
        try {
            if (!f.exists()) {
                f.createNewFile();
                System.out.println("new file created");
            } else {
                f.delete();
                f.createNewFile();
            }
            fw2 = new FileWriter(f);
            fw2.append("Fitness");
            fw2.append('\n');
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void saveBestFitnessToCSVByGP(String bestFitness) {
        try {

            if (count > 9) {
                fw.append("\n");
                count = 0;
            } else {
                fw.append(bestFitness);
                fw.append(",");
                count++;
            }

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void saveBestFitnessToCSVByIndiv(String bestFitness) {
        try {
            fw2.append(bestFitness);
            fw2.append("\n");

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void closeSaveCSVByGP() {
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeSaveCSVByIndiv() {
        try {
            fw2.close();
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //main method just used for testing
//    public static void main(String[] args) {
//        DataManager dm = new DataManager(6);
//
//        //reading file works fine!
//        //Rule[] rules = dm.readDataFileBool("data2.txt");
//        System.out.println("=========================");
//        //both methods work fine!
//        float[][] rules2 = dm.readDataFileBoolInts("data3.txt");
//    }
}
