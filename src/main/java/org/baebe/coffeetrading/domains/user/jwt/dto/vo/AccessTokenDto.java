package org.baebe.coffeetrading.domains.user.jwt.dto.vo;

import org.baebe.coffeetrading.commons.types.user.UserRole;

public record AccessTokenDto(
    Long id,
    UserRole role
) { }
