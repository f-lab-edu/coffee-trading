package org.baebe.coffeetrading.api.user.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.config.properties.NaverProperties;
import org.baebe.coffeetrading.api.user.dto.response.LoginResponse;
import org.baebe.coffeetrading.api.user.dto.response.NaverProfileResponse;
import org.baebe.coffeetrading.api.user.dto.response.NaverTokenResponse;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;
import org.baebe.coffeetrading.domains.user.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverAuthBusiness implements OAuth2LoginBusiness{

    private final NaverProperties naverProperties;
    private final UserBusiness userBusiness;
    private final UserService userService;
    private final AuthFacade authFacade;

    @Override
    public AccountTypes supports() {
        return AccountTypes.NAVER;
    }

    @Override
    public LoginResponse loginByOAuth(String code, AccountTypes accountTypes, String state) {

        String accessToken = toRequestAccessToken(code, state);
        NaverProfileResponse profileInfo = getProfileInfo(accessToken);

        if (profileInfo == null) {
            throw new CoreException(ErrorTypes.OAUTH_PROFILE_NOT_FOUND);
        }

        return userService.nullableGetByUserEmail(profileInfo.getResponse().getEmail())
            .map(ent -> {
                if (ent.getAccountType() == AccountTypes.NAVER) {
                    return authFacade.oauth2LoginHandle(profileInfo.getResponse().getEmail());
                }
                else if (ent.getAccountType() == AccountTypes.WEB) {
                    throw new CoreException(ErrorTypes.WEB_LOGIN_REQUEST);
                }
                else {
                    throw new CoreException(ErrorTypes.OAUTH_LOGIN_ERROR);
                }
            })
            .orElseGet(() -> {
                userBusiness.oauth2RegisterHandle(profileInfo);
                return authFacade.oauth2LoginHandle(profileInfo.getResponse().getEmail());
            });
    }

    private String toRequestAccessToken(String code, String state) {

        String clientId = naverProperties.getClientId();
        String clientSecret = naverProperties.getClientSecret();
        String tokenUrl = naverProperties.getTokenUrl();
        String redirectUri = naverProperties.getRedirectUri();
        String authorizationGrantType = naverProperties.getAuthorizationGrantType();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addIfAbsent("client_id", clientId);
        params.addIfAbsent("client_secret", clientSecret);
        params.addIfAbsent("grant_type", authorizationGrantType);
        params.addIfAbsent("state", state);
        params.addIfAbsent("code", code);
        params.addIfAbsent("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        NaverTokenResponse response =
            restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, NaverTokenResponse.class).getBody();

        if (response == null) {
            throw new CoreException(ErrorTypes.OAUTH_FAILED_LOGIN);
        }

        return response.getAccessToken();
    }

    private NaverProfileResponse getProfileInfo(String accessToken) {

        String profileUrl = "https://openapi.naver.com/v1/nid/me";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(profileUrl, HttpMethod.GET, entity, NaverProfileResponse.class).getBody();
    }
}
