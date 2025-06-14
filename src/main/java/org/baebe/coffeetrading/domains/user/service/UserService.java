package org.baebe.coffeetrading.domains.user.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.user.dto.response.MyStoreInfoResponse;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.domains.user.entity.UserStoreGroupsEntity;
import org.baebe.coffeetrading.domains.user.entity.UserStoresEntity;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;
import org.baebe.coffeetrading.domains.user.repository.UserStoreGroupsRepository;
import org.baebe.coffeetrading.domains.user.repository.UserStoresRepository;
import org.baebe.coffeetrading.domains.user.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final UserStoreGroupsRepository userStoreGroupsRepository;
    private final UserStoresRepository userStoresRepository;

    public UsersEntity getByUserId(Long userId) {
        return usersRepository.findById(userId)
            .orElseThrow(() -> new CoreException(ErrorTypes.USER_NOT_FOUND));
    }

    public UsersEntity getByUserEmail(String email) {
        return usersRepository.findByEmail(email)
            .orElseThrow(() -> new CoreException(ErrorTypes.USER_NOT_FOUND));
    }

    public Optional<UsersEntity> nullableGetByUserEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmailEqualsIgnoreCase(email);
    }

    public boolean existsByNickname(String nickname) {
        return usersRepository.existsByNickname(nickname);
    }

    public List<UsersEntity> getByUsernameAndBirthdayAndPhone(String userName, String birthDay, String phone) {
        return usersRepository.findByUserNameAndBirthDayAndPhone(userName, birthDay, phone);
    }

    @Transactional
    public UsersEntity saveUser(UsersEntity usersEntity) {
        return usersRepository.save(usersEntity);
    }


    public UserStoreGroupsEntity getUserStoreGroupsById(Long userStoreGroupId) {
        return userStoreGroupsRepository.findById(userStoreGroupId)
            .orElseThrow(() -> new CoreException(ErrorTypes.STORE_LIST_NOT_FOUND));
    }

    public boolean exitsByUserStoreGroupName(Long userId, String storeGroupName) {
        return userStoreGroupsRepository.existsByUserIdAndNameIgnoreCase(userId, storeGroupName);
    }

    public List<UserStoreGroupsEntity> getUserStoreGroupsByUserId(Long userId) {
        return userStoreGroupsRepository.findByUserId(userId);
    }

    public List<MyStoreInfoResponse> getUserStoresByUserStoreGroupId(Long userStoreGroupId) {
        return userStoresRepository.getMyStoreInfoByGroupId(userStoreGroupId);
    }

    public Optional<UserStoresEntity> getUserStoresByUserStoreGroupIdAndStoreId(Long userStoreGroupId, Long storeId) {
        return userStoresRepository.findByUserStoreGroup_IdAndStores_Id(userStoreGroupId, storeId);
    }

    @Transactional
    public void saveUserStoreList(UserStoreGroupsEntity ent) {
        userStoreGroupsRepository.save(ent);
    }

    @Transactional
    public void saveUserStore(UserStoresEntity ent) {
        userStoresRepository.save(ent);
    }

    @Transactional
    public void deleteUserStoreList(Long userStoreGroupId) {
        userStoresRepository.deleteByUserStoreGroupId(userStoreGroupId);
        userStoreGroupsRepository.deleteById(userStoreGroupId);
    }

    @Transactional
    public void deleteUserStore(Long groupId, Long storeId) {
        userStoresRepository.deleteByUserStoresIdAndUserStoreGroupId(groupId, storeId);
    }
}
