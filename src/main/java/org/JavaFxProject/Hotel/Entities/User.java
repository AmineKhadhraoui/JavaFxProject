package org.JavaFxProject.Hotel.Entities;

public class User {
    private String name;
    private String username;
    private String password;
    private String gender;
    private String securityQuestion;
    private String answer;
    private String address;
    private static String currentUserName;
    // Constructor
    public User(String name, String username, String password, String gender,
                String securityQuestion, String answer, String address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.address = address;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getSecurityQuestion() { return securityQuestion; }
    public void setSecurityQuestion(String securityQuestion) { this.securityQuestion = securityQuestion; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String name) {
        currentUserName = name;
    }
}
