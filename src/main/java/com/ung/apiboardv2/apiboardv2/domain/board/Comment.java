package com.ung.apiboardv2.apiboardv2.domain.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    private int id;
    private int memberId;
    private String nickname;
    private String comment;

}
