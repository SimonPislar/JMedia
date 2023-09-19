package com.JMedia.Security;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class CodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public CodeGenerator() {
        // Empty constructor
    }

    /*
        @Brief: This method is used to generate a random code.
        @Param: length - The length of the code to generate.
        @Return: String - Returns the generated code.
    */
    public String generateRandomCode(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }
}
