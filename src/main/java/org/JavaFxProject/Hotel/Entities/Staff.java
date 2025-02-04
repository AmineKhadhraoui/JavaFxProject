package org.JavaFxProject.Hotel.Entities;

public class Staff {
    private int staffID;
    private String name;
    private int age;
    private String gender;
    private String position;
    private String phone;
    private String email;

    public Staff(int staffID, String name, int age, String gender, String position, String phone, String email) {
        this.staffID = staffID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.position = position;
        this.phone = phone;
        this.email = email;
    }
    public int getStaffID() {
        return staffID;
    }
    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
