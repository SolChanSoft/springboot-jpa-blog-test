package com.cos.security1.service;

import com.cos.security1.domain.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌.
//IoC를 해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public int 회원가입(User user) {
        try {
            userRepository.save(user);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("UserService:회원가입() = " + e.getMessage());
        }
        return -1;
    }

    @Transactional(readOnly = true) //Select할때 트랙잰션시작, 서비스종료시에 트랜잭션 종료(정합성)
    public int 로그인(User user) {
        try {
            userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("UserService:로그인() = " + e.getMessage());
        }
        return -1;
    }

}
