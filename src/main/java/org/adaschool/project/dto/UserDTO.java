package org.adaschool.project.dto;

public class UserDTO {
    private final String username;
    private final String email;
    private final String password;

    public UserDTO() {
        this.username = "";
        this.email = "";
        this.password = "";
    }

    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
        this.password = "";
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
