package com.cos.security1.controller.api;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.domain.Board;
import com.cos.security1.domain.RoleType;
import com.cos.security1.domain.User;
import com.cos.security1.dto.ResponseDto;
import com.cos.security1.service.BoardService;
import com.cos.security1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board,
                                     @AuthenticationPrincipal PrincipalDetails principal ){
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
