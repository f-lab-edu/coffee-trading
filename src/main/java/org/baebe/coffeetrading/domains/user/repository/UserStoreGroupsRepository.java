package org.baebe.coffeetrading.domains.user.repository;

import java.util.List;
import org.baebe.coffeetrading.domains.user.entity.UserStoreGroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserStoreGroupsRepository extends JpaRepository<UserStoreGroupsEntity, Long> {

    List<UserStoreGroupsEntity> findByUserId(Long userId);

    @Query("SELECT DISTINCT g FROM UserStoreGroupsEntity g " +
        "JOIN FETCH g.stores us " +
        "JOIN FETCH us.stores " +
        "WHERE g.user.id = :userId")
    List<UserStoreGroupsEntity> findGroupsWithStoresByUserId(@Param("userId") Long userId);

    boolean existsByUserIdAndNameIgnoreCase(Long userId, String UserStoreGroupName);
}
