package com.ung.apiboardv2.apiboardv2.dto.board;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateRequest {
    @NotNull
    private int memberId;

    @NotNull
    private String title;

    @NotNull
    private String content;

}
