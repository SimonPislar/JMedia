package com.JMedia.Data.UserData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TwoFactorCodes {

    @Id
    @Column(unique=true)
    String email;

    String code;

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setCode(String code) {
        this.code=code;
    }
}
