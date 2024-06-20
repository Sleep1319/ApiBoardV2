package com.ung.apiboardv2.apiboardv2.controller.board;

import com.ung.apiboardv2.apiboardv2.dto.board.BoardCreateRequest;
import com.ung.apiboardv2.apiboardv2.dto.board.BoardDeleteRequest;
import com.ung.apiboardv2.apiboardv2.dto.board.BoardUpdateRequest;
import com.ung.apiboardv2.apiboardv2.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    //이미지 업로드가 formapi요청으로 만들기에 임시 생성)
    @PostMapping("/api/board/new")
    public ResponseEntity<String> createBoard (@Valid BoardCreateRequest req) {
        String message;
        boolean newBoard;

        newBoard = boardService.createBoard(req);

        if(!newBoard) {
            message = "게시판 등록 실패";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/api/board/edit")
    public ResponseEntity<String> updateBoard(@Valid @RequestBody BoardUpdateRequest req) {
        String message;
        boolean checkUpdate = boardService.updateBoard(req);
        if (!checkUpdate) {
            message = "게시판 정보 또는 멤버 정보가 불 일치";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/api/board/delete")
    public ResponseEntity<String> deleteBoard(@Valid @RequestBody BoardDeleteRequest req) {
        String message;
        boolean checkDelete = boardService.deleteBoard(req);
        if(!checkDelete) {
            message = "삭제하려는 게시판 정보 또는 유저 권환이 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
