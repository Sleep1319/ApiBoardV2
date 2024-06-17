package com.ung.apiboardv2.apiboardv2.domain.member;

import lombok.Data;

@Data
public class Member {
    private int id;

    private String email;

    private String password;

    private String username;

    private String nickname;

    public Member(int id, String email, String password, String username, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
    }
}
