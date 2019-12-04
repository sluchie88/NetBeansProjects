/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.studentscores;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author TomTom
 */
public class StudentList {

    static Scanner scrappyDoo = new Scanner(System.in);

    public static void main(String[] args) {
        String choice;
        Map<String, Student> studentList = new HashMap<>();

        Student first = new Student();
        Student second = new Student();
        Student third = new Student();
        Student fourth = new Student();
        Student fifth = new Student();

        first.setName("Jimmy Smith");
        first.setScore1(9.5);
        first.setScore2(8.0);
        first.setScore3(9.0);
        first.setScore4(7.5);
        studentList.put("Jimmy Smith", first);

        second.setName("Suzanne Orman");
        second.setScore1(6.5);
        second.setScore2(9.0);
        second.setScore3(5.0);
        second.setScore4(5.5);
        studentList.put("Suzanne Orman", second);

        third.setName("Seymore Butts");
        third.setScore1(10);
        third.setScore2(9.5);
        third.setScore3(10);
        third.setScore4(10);
        studentList.put("Seymore Butts", third);

        fourth.setName("Herbie Hancock");
        fourth.setScore1(6.5);
        fourth.setScore2(8.0);
        fourth.setScore3(9.0);
        fourth.setScore4(5.5);
        studentList.put("Herbie Hancock", fourth);

        fifth.setName("Fitz Farseer");
        fifth.setScore1(6.5);
        fifth.setScore2(9.0);
        fifth.setScore3(7.0);
        fifth.setScore4(8.5);
        studentList.put("Fitz Farseer", fifth);

        do {
            String sName;

            System.out.println("What would you like to do?");
            System.out.println("View all students [view], add a student [add], remove a student [rem],");
            System.out.println("View a student's scores [scores], View a student's average scores [avg], or Quit [q].");
            choice = scrappyDoo.nextLine();

            if (choice.equalsIgnoreCase("view")) {
                printAll(studentList);
            } else if (choice.equalsIgnoreCase("add")) {
                System.out.println("What is the new student's name?");
                sName = scrappyDoo.nextLine();
                if (!studentList.containsKey(sName)) {
                    System.out.println(sName + " does not exist in the list.");
                } else {
                    System.out.println(sName + " is already in the list.");
                }
                studentList = addStudent(studentList, sName);
            } else if (choice.equalsIgnoreCase("rem")) {
                System.out.println("Which student would you like to remove?");
                sName = scrappyDoo.nextLine();
                if (studentList.containsKey(sName)) {
                    studentList = removeStudent(studentList, sName);
                } else {
                    System.out.println(sName + " does not exist in the list.");
                }
            } else if (choice.equalsIgnoreCase("scores")) {
                System.out.println("Which student's scores would you like to view?");
                sName = scrappyDoo.nextLine();
                if (studentList.containsKey(sName)) {

                    studentScores(studentList, sName);
                } else {
                    System.out.println(sName + " does not exist in the list.");
                }
            } else if (choice.equalsIgnoreCase("avg")) {
                System.out.println("Which student's average score would you like to view?");
                sName = scrappyDoo.nextLine();
                if (studentList.containsKey(sName)) {

                    studentAvg(studentList, sName);
                } else {
                    System.out.println(sName + " does not exist in the list.");
                }
            }
        } while (!choice.equalsIgnoreCase("q"));
        System.out.println("Goodbye.");
    }

    public static void printAll(Map<String, Student> hMap) {
        Set<String> names = hMap.keySet();
        System.out.println("\n\nAll students in the class: ");
        System.out.println("_______________________________");
        for (String n : names) {
            System.out.println(n);
        }
        System.out.println("\n\nPlease press enter when you're ready to return to main menu.");
        scrappyDoo.nextLine();
    }

    //creates and adds a student to the hashmap, then returns it.
    //prompts user for quiz scores
    public static Map<String, Student> addStudent(Map<String, Student> hMap, String name) {
        double s1, s2, s3, s4;
        Student ns = new Student();

        System.out.println("What was " + name + "'s score on quiz 1?");
        s1 = scrappyDoo.nextDouble();
        ns.setScore1(s1);
        System.out.println("What was " + name + "'s score on quiz 2?");
        s2 = scrappyDoo.nextDouble();
        ns.setScore2(s2);
        System.out.println("What was " + name + "'s score on quiz 3?");
        s3 = scrappyDoo.nextDouble();
        ns.setScore3(s3);
        System.out.println("What was " + name + "'s score on quiz 4?");
        s4 = scrappyDoo.nextDouble();
        ns.setScore4(s4);

        hMap.put(name, ns);

        if (hMap.containsKey(name)) {
            System.out.println("\n\n" + name + " was added successfully.");
        }
        System.out.println("\n\nPlease press enter when you're ready to return to main menu.");
        scrappyDoo.nextLine();
        scrappyDoo.nextLine();
        return hMap;
    }

    //checks to make sure student exists in the map, if they do deletes them
    //sends out a message if suvvessfully deleted or if the student didn't exist
    public static Map<String, Student> removeStudent(Map<String, Student> hMap, String name) {
        hMap.remove(name);

        if (!hMap.containsKey(name)) {
            System.out.println("\n\n" + name + " was removed successfully.");
        }

        System.out.println("\n\nPlease press enter when you're ready to return to main menu.");
        scrappyDoo.nextLine();
        return hMap;
    }

    //prints out a specified student's scores
    public static void studentScores(Map<String, Student> hMap, String name) {
        double s1, s2, s3, s4;
        Student curr = hMap.get(name);
        s1 = curr.getScore1();
        s2 = curr.getScore2();
        s3 = curr.getScore3();
        s4 = curr.getScore4();

        System.out.println(name + "'s scores:");
        System.out.println("Quiz 1: " + s1);
        System.out.println("Quiz 2: " + s2);
        System.out.println("Quiz 3: " + s3);
        System.out.println("Quiz 4: " + s4);

        System.out.println("\n\nPlease press enter when you're ready to return to main menu.");
        scrappyDoo.nextLine();
    }

    //prints out a specified student's average on quiz scores
    public static void studentAvg(Map<String, Student> hMap, String name) {
        double s1, s2, s3, s4;
        Student curr = hMap.get(name);
        s1 = curr.getScore1();
        s2 = curr.getScore2();
        s3 = curr.getScore3();
        s4 = curr.getScore4();

        double avg = ((s1 + s2 + s3 + s4) / 4);

        System.out.println(name + "'s average is: " + avg);

        System.out.println("\n\nPlease press enter when you're ready to return to main menu.");
        scrappyDoo.nextLine();
    }
}
