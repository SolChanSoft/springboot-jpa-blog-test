package com.cos.security1.config.auth.oauth;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.config.auth.oauth.provider.*;
import com.cos.security1.domain.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    //구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    //함수종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException { //DI (의존성주입)
        System.out.println("userRequest.getClientRegistration() = " + userRequest.getClientRegistration());
        //구글로그인 버튼클릭 -> 구글로그인창 -> 로그인을 완료 -> code를 받음(OAuth-Client라이브러리)->AccessToken요청
        //userRequest정보를 받음 -> 회원 프로필을 받아야 함.(loadUser함수호출)->구글로부터 회원프로필을 받음.
        OAuth2User oauth2User = super.loadUser(userRequest);
        System.out.println("oauth2User.getAttributes() = " + oauth2User.getAttributes());
        //회원가입을 강제로 진행해볼 예정
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            System.out.println("페이스북 로그인 요청");
            oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oauth2User.getAttributes().get("response"));
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());
        }else {
            System.out.println("우리는 OAuth2 만 지원해요.");
        }

        System.out.println("oAuth2UserInfo.getProviderId() = " + oAuth2UserInfo.getProviderId());
        
        String provider = oAuth2UserInfo.getProvider(); //구글
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId; //구글_sub로 ID 구성
        String password = bCryptPasswordEncoder.encode("abcd");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";
        
        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }else{
            System.out.println("로그인을 이미 한적이 있습니다. 귀하는 자동회원가입이 되어 있습니다.");
        }

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }
}
