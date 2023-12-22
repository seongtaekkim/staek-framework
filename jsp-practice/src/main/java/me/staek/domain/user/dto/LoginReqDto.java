package me.staek.domain.user.dto;

import lombok.Data;

@Data
public class LoginReqDto {
    private String username;
    private String password;
}
