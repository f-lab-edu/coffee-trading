package org.baebe.coffeetrading.domains.user.service;

import static org.baebe.coffeetrading.commons.types.user.UserStatus.DISABLED;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.config.security.CustomUserDetails;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * SpringSecurity에서 로그인 가능여부를 처리하기 위해 제작해야 하는 구현체로, loadUserByUserName을 구현시
 * 직접 정의한 AuthenticationManager에서 AuthenticationProvider를 인증에 사용하는데
 * AuthenticationProvider에 이 Servie를 주입한 채로 Bean을 제작할 경우 내부의 authenticate 메소드만으로
 * 로그인 가능 여부의 구분이 가능해진다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UsersEntity user = userService.getByUserEmail(email);

        if (DISABLED.equals(user.getStatus())) {
            throw new CoreException(ErrorTypes.USER_NOT_FOUND);
        }

        return CustomUserDetails.of(
            user.getEmail(),
            user.getPassword(),
            user.getUserType()
        );
    }
}
