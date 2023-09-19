package com.JMedia.Security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordHandler {

    Argon2PasswordEncoder argon2PasswordEncoder;

    public PasswordHandler() {
        argon2PasswordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8(); // Encode with the default values
    }

    /*
        @Brief: This method is used to encrypt a password.
        @Param: password - The password to encrypt.
        @Return: String - Returns the encrypted password.
    */
    public String encrypt(String password) {
        return this.argon2PasswordEncoder.encode(password);
    }

    /*
        @Brief: This method is used to compare a password with an encrypted password.
        @Param: password - The password to compare.
        @Param: encodedPassword - The encrypted password to compare.
        @Return: boolean - Returns true if the passwords match.
    */
    public boolean comparePasswords(String password, String encodedPassword) {
        return this.argon2PasswordEncoder.matches(password, encodedPassword);
    }

}
