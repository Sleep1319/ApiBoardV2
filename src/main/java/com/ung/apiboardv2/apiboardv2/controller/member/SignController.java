package com.ung.apiboardv2.apiboardv2.controller.member;

import com.ung.apiboardv2.apiboardv2.dto.sign.SignInRequest;
import com.ung.apiboardv2.apiboardv2.dto.sign.SignInResponse;
import com.ung.apiboardv2.apiboardv2.dto.sign.SignUpRequest;
import com.ung.apiboardv2.apiboardv2.exception.LoginFailureException;
import com.ung.apiboardv2.apiboardv2.exception.MemberEmailAlreadyExistsException;
import com.ung.apiboardv2.apiboardv2.exception.MemberNicknameAlreadyExistsException;
import com.ung.apiboardv2.apiboardv2.service.SignService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SignController {
    private final SignService signService;

    @PostMapping("/api/sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest req) {
        String message = null;

        try {
            validateSingUpInfo(req);
        } catch (MemberEmailAlreadyExistsException e) {
            message = "중복된 이메일 입니다.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } catch (MemberNicknameAlreadyExistsException e) {
            message = "중복된 닉네임 입니다.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean signUpCheck = signService.signUp(req);
        if(!signUpCheck) {
            message = "회원 가입 실패";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private void validateSingUpInfo(SignUpRequest req) {
        if(signService.existsByEmail(req.getEmail())) {
            throw new MemberEmailAlreadyExistsException(req.getEmail());
        }
        if(signService.existsByNickname(req.getNickname()))
            throw new MemberNicknameAlreadyExistsException(req.getNickname());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String message = "입력값이 올바르지 않습니다.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @PostMapping("/api/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> signIn(@Valid @RequestBody SignInRequest req, HttpSession session) {
        String message = "일치 정보 없음";
        SignInResponse res = signService.signIn(req);
        if(res.getId() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            session.setAttribute("userInfo", res);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
