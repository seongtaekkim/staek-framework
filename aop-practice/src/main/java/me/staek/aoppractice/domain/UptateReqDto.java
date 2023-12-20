package me.staek.aoppractice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UptateReqDto {

    @NotBlank(message = "빈값이 왔습니다")
    @NotNull(message = "null")
    private String password;
    private String email;

    public UptateReqDto(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UptateReqDto{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
