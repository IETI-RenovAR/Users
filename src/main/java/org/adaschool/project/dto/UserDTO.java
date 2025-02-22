package org.adaschool.project.dto;

public class UserDTO {
    private final String name;
    private final String lastName;
    private final String email;
    private final String password;
    private final String userType; // "carpintero/tienda"

    public UserDTO() {
        this.name = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.userType = "";
    }

    public UserDTO(String name, String lastName, String email, String password, String userType) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public UserDTO(String name, String lastName, String email, String userType) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = "";
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
}