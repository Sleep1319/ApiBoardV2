package com.ung.apiboardv2.apiboardv2.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Member {
    private int id;

    private String email;

    private String password;

    private String username;

    private String nickname;

}
