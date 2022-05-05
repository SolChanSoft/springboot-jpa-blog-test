package com.cos.security1.controller.api;

import com.cos.security1.domain.RoleType;
import com.cos.security1.domain.User;
import com.cos.security1.dto.ResponseDto;
import com.cos.security1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user){
        System.out.println("UserApiController: save호출");
        // 실제로 DB에 insert를 하고 아래에서 return이 되면 되요.
        user.setRole(RoleType.ROLE_USER.name());
        int result = userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK, result); // 자바오브젝트를 JSON으로 변환해서 리턴(jackson)
    }
}
