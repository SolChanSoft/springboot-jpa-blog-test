package com.cos.security1.config.auth;

//시큐리티가 /login를 주소요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인을 진행이 완료가되면 시큐리티 session을 만들어 줍니다. (Security ContextHolder)
//오브젝트가 정해져있음. => Authentication 타입의 객체만 가능
//Authentication안에 User정보가 있어야 됨.
//User오브젝트 타입음 => UserDetails타입 객체

//security Session => Authentication => UserDetails

import com.cos.security1.domain.RoleType;
import com.cos.security1.domain.User;
import com.nimbusds.oauth2.sdk.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;  //콤포지션
    private Map<String, Object> attributes;

    // 일반 로그인시 사용하는 생성자
    public PrincipalDetails(User user){
        this.user = user;
    }

    // 구글등 OAuth를 이용해서 로그인하는 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  //비밀번호 계정이 기간이 지났니
    }

    @Override
    public boolean isEnabled() {  //계정이 활성화 되었니
        //우리 사이트!! 1년동안 회원이 로그인을 안하면!! 휴먼 계정으로 전전화하기로 함.
        //현재시간 - 로그인시간 => 1년을 초과하면 return false;
        return true;
    }


}
