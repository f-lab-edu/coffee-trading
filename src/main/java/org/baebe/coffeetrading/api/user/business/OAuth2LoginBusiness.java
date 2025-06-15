package org.baebe.coffeetrading.api.user.business;

import org.baebe.coffeetrading.api.user.dto.response.LoginResponse;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;

public interface OAuth2LoginBusiness {

    AccountTypes supports();

    LoginResponse loginByOAuth(String code, AccountTypes accountTypes, String state);
}
