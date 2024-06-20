package com.ung.apiboardv2.apiboardv2.dto.board;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardDeleteRequest {
    @NotNull
    private int boardId;

    @NotNull
    private int memberId;

}
