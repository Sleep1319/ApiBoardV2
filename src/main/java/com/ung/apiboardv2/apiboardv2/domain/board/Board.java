package com.ung.apiboardv2.apiboardv2.domain.board;

import lombok.Data;

@Data
public class Board {

    private int id;

    private int memberId;

    private String title;

    private String content;

    public Board(int id, int memberId, String title, String content) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }
}
