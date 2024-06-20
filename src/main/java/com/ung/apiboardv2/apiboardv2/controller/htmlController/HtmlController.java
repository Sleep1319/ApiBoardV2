package com.ung.apiboardv2.apiboardv2.controller.htmlController;

import com.ung.apiboardv2.apiboardv2.domain.board.Board;
import com.ung.apiboardv2.apiboardv2.dto.board.BoardGetAllResponse;
import com.ung.apiboardv2.apiboardv2.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HtmlController {
    private final BoardService boardService;

    @GetMapping("/")
    public String index(@RequestParam(required = false, name = "message") String message, Model model, HttpSession session) {
        if(message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        List<BoardGetAllResponse> res = boardService.getAllBoard();
        model.addAttribute("boardList", res);
        return "index";
    }

    @GetMapping("/sign-up")
    public String SignUp(@RequestParam(required = false, name = "message") String message, Model model, HttpSession session) throws UnsupportedEncodingException {
        if(session.getAttribute("userInfo") != null){
            String signInUser = "이미 로그인이 되어 있습니다";
            String encodedSignInUser = URLEncoder.encode(signInUser, "UTF-8");
            return "redirect:/?message=" + encodedSignInUser;
        }
        if(message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "signUp";
    }

    @GetMapping("/sign-in")
    public String signIn(@RequestParam(required = false, name = "message") String message , Model model, HttpSession session) throws UnsupportedEncodingException {
        if(session.getAttribute("userInfo") != null){
            String signInUser = "이미 로그인이 되어 있습니다";
            String encodedSignInUser = URLEncoder.encode(signInUser, "UTF-8");
            return "redirect:/?message=" + encodedSignInUser;
        }
        if(message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "signIn";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpSession session) throws UnsupportedEncodingException {
        session.invalidate();
        String signOut = "로그아웃 성공";
        String encodedSignOutUser = URLEncoder.encode(signOut, "UTF-8");
        return "redirect:/sign-in?message=" + encodedSignOutUser;
    }

    @GetMapping("/board/new")
    public String createBoard(@RequestParam(required = false, name = "message") String message, HttpSession session, Model model) throws UnsupportedEncodingException {
        if(session.getAttribute("userInfo") == null){
            String signInUser = "로그인후 이용 가능합니다";
            String encodedSignInUser = URLEncoder.encode(signInUser, "UTF-8");
            return "redirect:/sign-in?message=" + encodedSignInUser;
        }
        if(message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "new";
    }

    @GetMapping("/board/show/{id}")
    public String showBoard(@PathVariable int id, Model model) {
        Board board = boardService.getBoardById(id);
        if(board.getId() == 0) {
            model.addAttribute("message", "게시판을 찾을 수 없습니다");
            return "index";
        }
        model.addAttribute("board", board);
        return "show";
    }
}
