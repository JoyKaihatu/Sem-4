package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class testing {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("E:\\Bahan Kuliah (Semester 4) Git\\Sem-4\\Dump\\src\\com\\company\\FileBacaRader"));
        String line;


        while((line = reader.readLine()) != null){
            System.out.println(line);
        }
    }
}
