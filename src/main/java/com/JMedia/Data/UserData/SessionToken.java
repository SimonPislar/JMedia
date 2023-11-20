package com.JMedia.Data.UserData;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SessionToken {

    @Id
    private String email;

    private String token;

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setToken(String token) {
        this.token=token;
    }
}
