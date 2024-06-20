package com.ung.apiboardv2.apiboardv2.controller.board;

import com.ung.apiboardv2.apiboardv2.dto.comment.CommentCreateRequest;
import com.ung.apiboardv2.apiboardv2.dto.comment.CommentDeleteRequest;
import com.ung.apiboardv2.apiboardv2.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment/new")
    public ResponseEntity<String> newComment(@Valid @RequestBody CommentCreateRequest req) {
        String message;
        boolean newComment = commentService.createComment(req);

        if (!newComment) {
            message = "등록 실패";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/api/comment/delete")
    public ResponseEntity<String> deleteComment(@Valid @RequestBody CommentDeleteRequest req) {
        String message;
        boolean checkDelete = commentService.deleteService(req);
        if(!checkDelete) {
            message = "삭제하려는 댓글 정보 또는 유저 권환이 없습니다";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
