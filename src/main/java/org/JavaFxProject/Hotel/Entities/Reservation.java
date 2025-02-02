package org.JavaFxProject.Hotel.Entities;

public class Reservation {
    private int resID;
    private int roomNumber;
    private String customerName;
    private int customerIDNumber;
    private String checkInDate;
    private String checkOutDate;

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    private int totalDays;
    private double totalPrice;
    private String status;

    public Reservation(int resID, int roomNumber, int customerIDNumber, String checkInDate, String checkOutDate,int totalDays, double totalPrice, String status ,String  customerName ) {
        this.resID = resID;
        this.roomNumber = roomNumber;
        this.customerIDNumber = customerIDNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalDays = totalDays;
        this.totalPrice = totalPrice;
        this.status = status;
        this.customerName=customerName;
    }

    public int getResID() {
        return resID;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getCustomerIDNumber() {
        return customerIDNumber;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setCustomerIDNumber(int customerIDNumber) {
        this.customerIDNumber = customerIDNumber;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
