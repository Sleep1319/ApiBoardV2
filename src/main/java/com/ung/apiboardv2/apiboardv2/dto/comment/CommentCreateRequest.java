package com.ung.apiboardv2.apiboardv2.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CommentCreateRequest {
    @NotNull(message = "게시글 정보 조회 불가")
    private int boardId;

    @NotNull(message = "회원정보 조회 불가")
    private int memberId;

    @NotNull(message = "댓글을 써주세요")
        private String comment;

    }
