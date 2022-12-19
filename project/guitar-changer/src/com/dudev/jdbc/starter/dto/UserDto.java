package com.dudev.jdbc.starter.dto;

public class UserDto {

    private String firstName;
    private String lastName;
    private String username;
    private String role;

    public UserDto(String firstName, String lastName, String username, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }
}
