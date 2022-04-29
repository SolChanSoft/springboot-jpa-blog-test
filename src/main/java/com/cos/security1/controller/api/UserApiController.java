package com.cos.security1.controller.api;

import com.cos.security1.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
    public ResponseDto<Integer> save(@RequestBody User user){
        System.out.println("UserApiController: save호출");
        return new ResponseDto<Integer>(HttpStatus.OK, 1);
    }
}
