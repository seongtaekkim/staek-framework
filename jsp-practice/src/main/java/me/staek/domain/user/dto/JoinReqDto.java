package me.staek.domain.user.dto;

import lombok.Data;

@Data
public class JoinReqDto {
    private String username;
    private String password;
    private String email;
    private String address;
}
