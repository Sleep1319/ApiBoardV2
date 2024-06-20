package com.ung.apiboardv2.apiboardv2.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardGetAllResponse {

    private int id;
    private String title;
    private String nickname;

}
