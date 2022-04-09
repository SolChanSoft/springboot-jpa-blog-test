package com.cos.security1.config.auth;


import com.cos.security1.domain.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티에서 설정에서 loginProcessingUrl("/login");
// /login요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어있는 loadUserByUsername함수가 실행

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //username을 변경하면 작동이 안될 수 있음. 정 바꾸고 싶으면
    //SecurityConfig에서 .usernameParameter("username2")로 바꾸면 됨.  username2는 예시임.
    //시큐리티 session(내부 Authentication(내부 UserDetails))
    //함수종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //유저가 있는지 확인
        User userEntity = userRepository.findByUsername(username);
        if(username != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
