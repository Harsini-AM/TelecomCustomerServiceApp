package com.example.EmployeeService.service;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Random;
 
@Service
public class PasswordService {
 
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
 
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    private static final Random RANDOM = new SecureRandom();
 
    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randomIndex = RANDOM.nextInt(PASSWORD_ALLOW_BASE.length());
            password.append(PASSWORD_ALLOW_BASE.charAt(randomIndex));
        }
        return password.toString();
    }
}
