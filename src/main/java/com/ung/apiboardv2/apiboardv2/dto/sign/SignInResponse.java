package com.ung.apiboardv2.apiboardv2.dto.sign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse implements Serializable {
    private int id;
    private String email;
    private String username;
    private String nickname;
    private String roleName;
}
