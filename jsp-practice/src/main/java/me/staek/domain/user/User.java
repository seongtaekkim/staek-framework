package me.staek.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private int id;
    private String username;
    private String pasword;
    private String email;
    private String address;
    private String userRole; // admin, user
    // private Role userRole; // enum Role 로 해보자
    private Timestamp createDate;
}
