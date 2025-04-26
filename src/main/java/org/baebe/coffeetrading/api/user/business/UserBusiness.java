package org.baebe.coffeetrading.api.user.business;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.user.dto.request.MobileAuthenticationRequest;
import org.baebe.coffeetrading.api.user.dto.request.ModifyPasswordRequest;
import org.baebe.coffeetrading.api.user.dto.request.RegisterRequest;
import org.baebe.coffeetrading.api.user.dto.response.NaverProfileResponse;
import org.baebe.coffeetrading.api.user.dto.response.RegisterResponse;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;
import org.baebe.coffeetrading.commons.types.user.GenderTypes;
import org.baebe.coffeetrading.commons.types.user.UserRole;
import org.baebe.coffeetrading.commons.types.user.UserStatus;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;
import org.baebe.coffeetrading.domains.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBusiness {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponse registerHandle(RegisterRequest registerRequest) {

        checkDuplicateEmail(registerRequest.getEmail());
        checkDuplicateNickname(registerRequest.getNickname());
        final UsersEntity registerUser = toUserEntityByRegisterRequest(registerRequest);
        UsersEntity result = userService.saveUser(registerUser);
        return RegisterResponse.of(result);
    }

    @Transactional
    public void oauth2RegisterHandle(NaverProfileResponse profileInfo) {

        checkDuplicateEmail(profileInfo.getEmail());
        checkDuplicateNickname(profileInfo.getNickname());

        UsersEntity registerUser = UsersEntity.builder()
            .email(profileInfo.getEmail())
            .password(null)
            .userName(profileInfo.getName())
            .phone(profileInfo.getMobile())
            .gender(conversionNaverGenderType(profileInfo.getGender()))
            .nickname(profileInfo.getNickname())
            .status(UserStatus.ENABLED)
            .accountType(AccountTypes.NAVER)
            .userType(UserRole.USER)
            .build();

        userService.saveUser(registerUser);
    }

    public boolean isExistEmail(String email) {
        try {
            checkDuplicateEmail(email);
            return true;
        } catch (CoreException e) {
            if (ErrorTypes.DUPLICATE_USER_ID.equals(e.getErrorTypes())) {
                return false;
            }
            throw e;
        }
    }

    public boolean isExistNickname(String nickname) {
        try {
            checkDuplicateNickname(nickname);
            return true;
        } catch (CoreException e) {
            if (ErrorTypes.DUPLICATE_NICKNAME.equals(e.getErrorTypes())) {
                return false;
            }
            throw e;
        }
    }

    /**
     * 본인 인증
     */
    @Transactional
    public void authenticationByMobile(String userId, MobileAuthenticationRequest mobileAuthenticationRequest) {

        UsersEntity user = checkDuplicateMobileAuthentication(userId,
            mobileAuthenticationRequest.getUserName(),
            mobileAuthenticationRequest.getBirthday(), mobileAuthenticationRequest.getPhone());

        user = UsersEntity.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .userName(mobileAuthenticationRequest.getUserName())
            .phone(mobileAuthenticationRequest.getPhone())
            .birthDay(mobileAuthenticationRequest.getBirthday())
            .gender(mobileAuthenticationRequest.getGender())
            .nickname(user.getNickname())
            .status(user.getStatus())
            .accountType(user.getAccountType())
            .userType(user.getUserType())
            .build();

        userService.saveUser(user);
    }

    /**
     * 중복 본인 인증 방지
     * 본인 인증을 시도한 사용자의 정보가 DB에 존재하는지 확인하고
     * 없으면 UsersEntity를 반환, 있으면 에러 반환됨
     */
    public UsersEntity checkDuplicateMobileAuthentication(String userId, String userName, String birthDay, String phone) {

        if (!StringUtils.hasText(userId)) {
            throw new CoreException(ErrorTypes.BAD_REQUESTS);
        }

        List<UsersEntity> userList = userService.getByUsernameAndBirthdayAndPhone(
            userName, birthDay, phone);

        if (userList.isEmpty()) {
            return userService.getByUserId(Long.parseLong(userId));
        }
        else if (userList.size() > 1) {
            throw new CoreException(ErrorTypes.MULTI_USER_ERROR);
        }
        throw new CoreException(ErrorTypes.DUPLICATE_USER);
    }

    @Transactional
    public void modifyPassword(String userId, ModifyPasswordRequest modifyPasswordRequest) {

        String oldPassword = modifyPasswordRequest.getOldPassword();
        String newPassword = modifyPasswordRequest.getNewPassword();

        if (!StringUtils.hasText(userId)) {
            throw new CoreException(ErrorTypes.BAD_REQUESTS);
        }

        UsersEntity user = userService.getByUserId(Long.parseLong(userId));

        boolean isEqualsOldPassword = isEqualsPassword(oldPassword, user.getPassword());

        if (!isEqualsOldPassword) {
            throw new CoreException(ErrorTypes.NOT_EQUALS_PASSWORD);
        }

        boolean isEqualsNewPassword = isEqualsPassword(newPassword, user.getPassword());

        if (isEqualsNewPassword) {
            throw new CoreException(ErrorTypes.CHANGE_PASSWORD_EQUALS);
        }

        user = UsersEntity.builder()
            .email(user.getEmail())
            .password(passwordEncoder.encode(newPassword))
            .userName(user.getUserName())
            .phone(user.getPhone())
            .birthDay(user.getBirthDay())
            .gender(user.getGender())
            .nickname(user.getNickname())
            .status(user.getStatus())
            .accountType(user.getAccountType())
            .userType(user.getUserType())
            .build();

        userService.saveUser(user);
    }

    @Transactional
    public void disableUser(String userId) {

        if (!StringUtils.hasText(userId)) {
            throw new CoreException(ErrorTypes.BAD_REQUESTS);
        }

        UsersEntity user = userService.getByUserId(Long.parseLong(userId));

        user = UsersEntity.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .userName(user.getNickname())
            .birthDay(user.getBirthDay())
            .gender(user.getGender())
            .nickname(user.getNickname())
            .status(UserStatus.DISABLED)
            .accountType(user.getAccountType())
            .userType(user.getUserType())
            .build();

        userService.saveUser(user);
    }

    private void checkDuplicateNickname(String nickname) {
        if (userService.existsByNickname(nickname)) {
            throw new CoreException(ErrorTypes.DUPLICATE_NICKNAME);
        }
    }

    private void checkDuplicateEmail(String email) {
        if (userService.existsByEmail(email)) {
            throw new CoreException(ErrorTypes.DUPLICATE_USER_ID);
        }
    }

    private UsersEntity toUserEntityByRegisterRequest(RegisterRequest request) {

        String encodePassword = passwordEncoder.encode(request.getPassword());

        return UsersEntity.ofUser(
            request.getEmail(),
            encodePassword,
            request.getNickname(),
            request.getAccountType(),
            request.getUserType()
        );
    }

    private boolean isEqualsPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    private GenderTypes conversionNaverGenderType(String gender) {

        return switch (gender) {
            case "F" -> GenderTypes.WOMAN;
            case "M" -> GenderTypes.MAN;
            default -> null;
        };
    }
}