package org.JavaFxProject.Hotel.Entities;

public class Room {
    private int number;
    private int price;
    private String type;
    private String status;

    public Room(int roomNumber, int price, String roomType, String status) {
        this.number = roomNumber;
        this.price = price;
        this.type = roomType;
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
