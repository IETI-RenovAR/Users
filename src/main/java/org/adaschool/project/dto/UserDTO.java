package org.adaschool.project.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adaschool.project.model.RoleEnum;

public class UserDTO {
    private final String username;
    private final String email;
    private final String password;
    private List<RoleEnum> roles;

    public UserDTO() {
        this.username = "";
        this.email = "";
        this.roles = new ArrayList<>(Collections.singleton(RoleEnum.USER));
        this.password = "";
    }

    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.roles = new ArrayList<>(Collections.singleton(RoleEnum.USER));
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

        public List<RoleEnum> getRoles() {
        return roles;
    }

    public void addRole(RoleEnum role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

}
