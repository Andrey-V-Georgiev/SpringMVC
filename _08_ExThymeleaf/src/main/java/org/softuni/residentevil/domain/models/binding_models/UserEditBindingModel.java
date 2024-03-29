package org.softuni.residentevil.domain.models.binding_models;

import java.util.Set;

public class UserEditBindingModel {

    private String id;
    private String username;
    private String email;
    private String authorities;

    public UserEditBindingModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
