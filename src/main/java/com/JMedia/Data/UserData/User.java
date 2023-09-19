package com.JMedia.Data.UserData;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String displayName;

    private String password;

    @Column(unique=true)
    private String email;

    private String firstName;

    private String lastName;

    public Integer getId() {
        return id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    protected void setUsername(String displayName) {
        this.displayName=displayName;
    }

    protected void setPassword(String password) {
        this.password=password;
    }

    protected void setEmail(String email) {
        this.email=email;
    }

    protected void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    protected void setLastName(String lastName) {
        this.lastName=lastName;
    }
}
