package org.JavaFxProject.Hotel.Entities;

public class Customer {
    private int customerIDNumber;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNo;
    private String customerGender;
    private String customerNationality;

    public Customer(int customerIDNumber, String customerName, String customerEmail, String customerPhoneNo, String customerGender, String customerNationality) {
        this.customerIDNumber = customerIDNumber;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhoneNo = customerPhoneNo;
        this.customerGender = customerGender;
        this.customerNationality = customerNationality;
    }

    public int getCustomerIDNumber() {
        return customerIDNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public String getCustomerNationality() {
        return customerNationality;
    }

    public void setCustomerIDNumber(int customerIDNumber) {
        this.customerIDNumber = customerIDNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public void setCustomerNationality(String customerNationality) {
        this.customerNationality = customerNationality;
    }
}
