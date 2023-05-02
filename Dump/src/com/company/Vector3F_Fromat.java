package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Vector3F_Fromat {
    public static void main(String[] args) {
        File make = new File("vertc_afterFormat.txt")
        try {
            Scanner myreader = new Scanner("vertc_shepere");
            while(myreader.hasNextLine()){
                String data = myreader.nextLine();

                FileWriter wa = new FileWriter()

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
