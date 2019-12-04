/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classroster.dto;

/**
 *
 * @author TomTom
 */
public class Student {
    private String firstName;
    private String lastName;
    private String studentID;
    private String cohort;
    
    public Student(String studentID){
        this.studentID = studentID;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getStudentID(){
        return studentID;
    }
    
    public String getCohort(){
        return cohort;
    }
    
    public void setCohort(String cohort){
        this.cohort = cohort;
    }
}
