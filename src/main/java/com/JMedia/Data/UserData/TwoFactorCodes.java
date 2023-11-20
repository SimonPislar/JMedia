package com.JMedia.Data.UserData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TwoFactorCodes {

    @Id
    @Column(unique=true)
    private String email;

    private String code;

    private String setTime;

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public String getSetTime() {
        return setTime;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setCode(String code) {
        this.code=code;
    }

    public void setSetTime(String setTime) {
        this.setTime=setTime;
    }
}
