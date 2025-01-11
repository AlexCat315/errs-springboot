package com.x.backend.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomCodeGeneratorUtils {

    private static final String CHARACTERS = generateCharacterSet();
    private static final int CODE_LENGTH = 6;

    private static String generateCharacterSet() {
        StringBuilder characters = new StringBuilder();
        for (char c = 'A'; c <= 'Z'; c++) {
            characters.append(c);
        }
        for (char c = 'a'; c <= 'z'; c++) {
            characters.append(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            characters.append(c);
        }
        return characters.toString();
    }

    public String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder randomCode = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            randomCode.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return randomCode.toString();
    }

    public String generateRandomCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder randomCode = new StringBuilder(length);

        for (int i = 0; i < CODE_LENGTH; i++) {
            randomCode.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return randomCode.toString();
    }
}