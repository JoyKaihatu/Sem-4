package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // write your code here
        No1_C14220227 tes = new No1_C14220227();

        System.out.println("Input nama file");
        String namaFile = scanner.nextLine();
        System.out.println("Input nama");
        String nama = scanner.nextLine();
        System.out.println("Input NRP");
        String nrp =  scanner.nextLine();

        tes.insert(namaFile,nama,nrp);
    }
}


class No1_C14220227{

    String filename,nama,nrp;
    int index = 0;
    public No1_C14220227(){

    }



    public void insert(String filename, String nama, String nrp){
        this.filename = filename;
        this.nama = nama;
        this.nrp = nrp;

        File file = new File(filename);

        try {
            FileWriter writer = new FileWriter(filename,true);
            writer.write(nama + "\n");
            writer.write(nrp + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Scanner myreader = new Scanner(file);
            while(myreader.hasNextLine()){
                String data = myreader.nextLine();
                if(index == 0){
                    System.out.println("Nama : " + data);
                    index += 1;
                }
                else{
                    System.out.println("NRP : " + data);
                    index = 0;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
