package com.cos.security1.config;

import com.cos.security1.config.auth.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity  //활성화시킴,   스프링시큐리티필터(SecurityConfig)가 스프링필터체인지에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화, preAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //패스워드 암호화 작업
    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable(); //비활성화시킴
        http.authorizeHttpRequests()
                .antMatchers("/user/**").authenticated() //인증만 되면 들어갈 수 있는 주소
                .antMatchers("/manager/**").hasAnyAuthority("ROLE_ADMIN","ROLE_MANAGER")
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") //login주소가 호출이 되면서 시큐리티가 낚아채서 대신 로그인을 진행해줌
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/user") //성공하면 메인페이지로 이동 , 구글 로그인이 완료된 후 처리가 필요함. Tip: 코드 X, (엑세스토큰+사용자프로필정보 O)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
    }
}
