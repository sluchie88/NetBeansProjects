/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.isbnchecker;

/**
 *
 * @author TomTom
 */
public class ISBNChecker {

    public static void main(String[] args) {
        String str = "0-306-40615-2";
        System.out.println(str);
        str = removeJunk(str);
        System.out.println(str);
        int result = CheckISBN(str);
        System.out.println(result);
    }

    public static int CheckISBN(String digits) {
        int ISBN = 0;
        int counter = 10;
        for (int i = 0; counter > 0; i++) {
            char c = digits.charAt(i);
            int something = Character.getNumericValue(c);
            ISBN += something * counter;
            counter--;
        }
        ISBN = (11 - (ISBN % 11)) % 11;
        return ISBN;
    }

    public static String removeJunk(String ISBN) {
        String newString = "";
        for (int i = 0; i < ISBN.length(); i++) {
            if (ISBN.charAt(i) == '-') {
            } else {
                newString += ISBN.charAt(i);
            }
        }
        return newString;
    }
}
