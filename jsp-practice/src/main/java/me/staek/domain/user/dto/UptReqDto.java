package me.staek.domain.user.dto;

import lombok.Data;

@Data
public class UptReqDto {
    private String password;
    private String email;
    private String address;
}
