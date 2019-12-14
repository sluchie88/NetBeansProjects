package org.g10.lottery.models;

public class Customer {

    /*
    customer class. pretty straightforward
    
    I added the field for the zip codes. However, in my attempt to generate zip codes rather than 
    create new data from Mockaroo, I found that I needed two constructors. Once I have added
    zips to all customers, I believe I can refactor and have one constructor with 4 params
    */
    
    private String emailAddress;
    private String zipCode;
    private String firstName;
    private String lastName;

    public Customer() {
    }

     public Customer(String emailAddress, String firstName, String lastName) {
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Customer(String emailAddress, String zip, String firstName, String lastName) {
        this.emailAddress = emailAddress;
        this.zipCode = zip;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
