package me.staek.aoppractice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class JoinReqDto {

    @NotNull(message = "empty username")
    @NotBlank(message = "please insert username")
    @Size(max=20, message="초과했습니다.")
    private String username;

    @NotNull(message = "empty password")
    private String password;
    private String email;

    public JoinReqDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "JoinReqDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
