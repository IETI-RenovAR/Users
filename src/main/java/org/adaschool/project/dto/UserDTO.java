package org.adaschool.project.dto;

public class UserDTO {
    private final String name;
    private final String lastName;
    private final String email;
    private final String password;

    public UserDTO() {
        this.name = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
    }

    public UserDTO(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserDTO(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = "";
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

}
