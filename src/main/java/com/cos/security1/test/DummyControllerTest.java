package com.cos.security1.test;

import com.cos.security1.domain.RoleType;
import com.cos.security1.domain.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

//사용자가 요청 -> 응답(html파일)
//@Controller

//사용자가 요청 -> 응답(Data)
@RestController  //
public class DummyControllerTest {

    @Autowired //의존성 주입
    private UserRepository userRepository;

    //{id} 주소로 파라메터를 전달 받을 수 있음.
    // http://localhost:8080/user/3
    @GetMapping("/user1/{id}")
    public User detail(@PathVariable int id){

        // user/4를 찾으면 내가 데이터베이스에서 못 차아오게 되면 userrk null이 될 것 아냐?
        // 그럼 return null이 리턴이 되자나.  그럼 프로그램에 문제가 있으니?
        // Optional 로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판다해서 return해
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalStateException>() {
            @Override
            public IllegalStateException get() {
                return new IllegalStateException("해당유저는 없습니다. id: " + id);
            }
        });
        return user;
    }


    //http://localhost:8080/join (요청)
    //http의 body에 username, password, email 데이터를 가지고 요청
    @PostMapping("/join1")
    public String join(User user){
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());

        user.setRole("ROLE_USER"); //기본값으로 회원가입시 바로 입력
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
