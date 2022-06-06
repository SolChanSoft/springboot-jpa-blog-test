package com.cos.security1.repository;

import com.cos.security1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


//CRUD 함수를 JpaRepository가 들고 있음.
//DAO
//자동으로 bean등록이 된다
// @Repository 생략이 가능
public interface UserRepository extends JpaRepository<User, Integer> {
    //findBy는 규칙이면 -> Username는 문법임
    //select * from user where username = 1?
    //Jpa query 메서드 규칙
    public User findByUsername(String username);

    // select * from user where email = ?
    //public User findByEmail();

    //JPA Naming 전략(쿼리)
    // Select * from user where username = ?1(username) AND passowrd = ?2(password) 형식으로 호출
    // 다른 방법으로는
    // @Query(value="SELECT * FROM user WHERE username = ?1 AND  passowrd = ?2", nativeQuery= true)
    // User login(String username, String password);
    User findByUsernameAndPassword(String username, String password);
}
