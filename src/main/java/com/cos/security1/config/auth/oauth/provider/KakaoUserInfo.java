package com.cos.security1.config.auth.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    //getAttributes()을 받을것임. oauth2User가 가지고 있는 attributes값
    private Map<String, Object> attributes;
    private Map<String,Object> attributesAccount;
    private Map<String,Object> attributesProfile;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return attributesAccount.get("email").toString();
    }

    @Override
    public String getName() {
        return attributesProfile.get("nickname").toString();
    }
}
