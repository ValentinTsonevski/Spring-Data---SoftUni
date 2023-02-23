package com.example.gamestore.domain.dtos;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.gamestore.Constants.*;


public class UserRegisterDTO {

private String email;
private String password;
private String confirmPassword;
private String fullName;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String email, String password, String confirmPassword, String fullName) {
        setEmail(email);
        setPassword(password);
        setConfirmPassword(confirmPassword);
        setFullName(fullName);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_VALIDATION);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new IllegalArgumentException(INCORRECT_EMAIL);
        }

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_VALIDATION);
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()){
            throw new IllegalArgumentException(INCORRECT_PASSWORD);
        }

        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {

        if(!this.password.equals(confirmPassword)){
            throw new IllegalArgumentException(INCORRECT_PASSWORD);
        }

        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
