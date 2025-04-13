package org.baebe.coffeetrading.domains.user.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;
import org.baebe.coffeetrading.domains.user.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public UsersEntity getByUserId(Long userId) {
        return usersRepository.findById(userId)
            .orElseThrow(() -> new CoreException(ErrorTypes.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public UsersEntity getByUserEmail(String email) {
        return usersRepository.findByEmail(email)
            .orElseThrow(() -> new CoreException(ErrorTypes.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Optional<UsersEntity> nullableGetByUserEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmailEqualsIgnoreCase(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByNickname(String nickname) {
        return usersRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public List<UsersEntity> getByUsernameAndBirthdayAndPhone(String userName, String birthDay, String phone) {
        return usersRepository.findByUserNameAndBirthDayAndPhone(userName, birthDay, phone);
    }

    @Transactional
    public UsersEntity saveUser(UsersEntity usersEntity) {
        return usersRepository.save(usersEntity);
    }
}
