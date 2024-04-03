package com.haedream.haedream.dto;

import com.haedream.haedream.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity; // 사용자 엔터티 객체를 보관하는 변수

    // 생성자
    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity; // 사용자 엔터티 객체 초기화
    }

    // 사용자의 권한 정보를 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>(); // 권한 컬렉션 생성
        collection.add(new GrantedAuthority() { // 사용자의 권한을 추가
            @Override
            public String getAuthority() {
                return userEntity.getRole(); // 사용자의 역할(role) 반환
            }
        });
        return collection; // 권한 컬렉션 반환
    }

    // 사용자의 비밀번호를 반환하는 메서드
    @Override
    public String getPassword() {
        return userEntity.getPassword(); // 사용자의 비밀번호 반환
    }

    // 사용자의 아이디를 반환하는 메서드
    @Override
    public String getUsername() {
        return userEntity.getUsername(); // 사용자의 아이디 반환
    }

    // 사용자 계정이 만료되지 않았는지 확인하는 메서드
    @Override
    public boolean isAccountNonExpired() {
        return true; // 사용자 계정이 만료되지 않았음을 반환
    }

    // 사용자 계정이 잠기지 않았는지 확인하는 메서드
    @Override
    public boolean isAccountNonLocked() {
        return true; // 사용자 계정이 잠기지 않았음을 반환
    }

    // 사용자의 자격 증명(비밀번호)이 만료되지 않았는지 확인하는 메서드
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 사용자의 자격 증명(비밀번호)이 만료되지 않았음을 반환
    }

    // 사용자 계정이 활성화되었는지 확인하는 메서드
    @Override
    public boolean isEnabled() {
        return true; // 사용자 계정이 활성화되었음을 반환
    }
}