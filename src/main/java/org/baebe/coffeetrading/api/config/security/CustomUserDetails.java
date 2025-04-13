package org.baebe.coffeetrading.api.config.security;

import java.util.Collection;
import java.util.Collections;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.commons.types.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public static CustomUserDetails of(String email, UserRole role) {
        return of(email, "", role);
    }

    public static CustomUserDetails of(String email, String password, UserRole role) {
        return CustomUserDetails.builder().email(email).password(password).role(role).build();
    }
}
