package org.adaschool.project.model;

import org.adaschool.project.dto.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private List<RoleEnum> roles;

    public UserEntity(){
    }

    public UserEntity(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = new ArrayList<>(Collections.singleton(RoleEnum.USER));
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public UserEntity(UserDTO userDTO){
        this.id = null;
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.roles = new ArrayList<>(Collections.singleton(RoleEnum.USER));
        this.password = new BCryptPasswordEncoder().encode(userDTO.getPassword());
    }

    public String getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public void addRole(RoleEnum role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    public void update(UserDTO userDTO){
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.password = new BCryptPasswordEncoder().encode(userDTO.getPassword());
    }
}
