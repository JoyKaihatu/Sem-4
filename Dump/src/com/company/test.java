package com.company;

import java.util.Scanner;

public class test {
     public static Boolean cekUlang(String kata){
        int panjang = kata.length();
        if (panjang % 2 != 0){

            int SetelahBagi = panjang/2;

            if(kata.charAt(SetelahBagi) == '-'){
                return true;
            }

            return false;
        }
        else{
            System.out.println("Bukan Ganjil");
            return false;
        }
    }

    public static Boolean cekUlangSempurna(String kata){
         String kata1,kata2;
         int panjang = kata.length();
         int SetelahBagi = panjang/2;

         kata1 = kata.substring(0,SetelahBagi);
         kata2 = kata.substring(SetelahBagi+1);

         if(kata1.equalsIgnoreCase(kata2)){
             return true;
         }
         return false;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String trima = scan.nextLine();

        Boolean balik = cekUlang(trima);

        if(balik){
            Boolean balik2 = cekUlangSempurna(trima);
            if(balik2){
                System.out.println("Kata "+ trima + " berulang sempurna");

            }
            else{
                System.out.println("Kata "+ trima + " berulang");
            }

        }

    }
}

