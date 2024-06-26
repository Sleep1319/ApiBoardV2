package com.ung.apiboardv2.apiboardv2.domain.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Board {

    private int id;

    private String title;

    private String content;

    private int memberId;

    private String nickname;

    private List<Comment> comments;

}
