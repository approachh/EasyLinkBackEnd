package com.easylink.easylink.dtos; //Updated 27.03

public class LoginDTO {
     private String email;
     private String password;
     private String firstName; //Updated 30.03.25


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
