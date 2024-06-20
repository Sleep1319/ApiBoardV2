package com.ung.apiboardv2.apiboardv2.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDeleteRequest {
    @NotNull
    private int commentId;

    @NotNull
    private int boardId;

    @NotNull
    private int memberId;

}
