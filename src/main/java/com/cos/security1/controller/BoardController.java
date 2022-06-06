package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //View를 리턴하겠다
public class BoardController {
//
//    @GetMapping({"","/"})
//    public String index() {
//        return "index";
//    }

    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
}
