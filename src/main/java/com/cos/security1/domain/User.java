package com.cos.security1.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter   @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// @DynamicInsert Insert 쿼리를 보낼때 NULL인 필드을 제회시킴
public class User {
    @Id  // PK설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링전략을 따라간다.
    @Column(name = "user_id")
    private Long id;  //예시: "google_10~~"

    //무조건 NULL이 안되면서 최대길이가 20자가 안되게, unique 중복방지
    @Column(nullable = false, length = 255, unique = true)
    private String username;
    private String password;  //구글등으로 로그인할 경우 NULL대신에 임의의 암호로 입력

    @Column(nullable = false, length = 50)
    private String email;
//
//    @Enumerated(EnumType.STRING)
//    private RoleType role; //ROLE_USER, ROLE_ADMIN, ROLE_MANAGER
    private String role; //ROLE_USER, ROLE_ADMIN, ROLE_MANAGER
    private String provider;  //google
    private String providerId;  //구글용 아이디  sub정보  //강제회원을 가입시킴.
//    private Timestamp loginDate;  로그인한 시간을 기록

    @CreationTimestamp  //가입한 시간(현재시간) - 시간이 자동입력
    private Timestamp createDate;

    @Builder
    public User (String username, String password, String email, String role, String provider, String providerId,
                Timestamp createDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }

}
