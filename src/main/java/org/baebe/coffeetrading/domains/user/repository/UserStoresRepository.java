package org.baebe.coffeetrading.domains.user.repository;

import java.util.List;
import java.util.Optional;
import org.baebe.coffeetrading.domains.user.entity.UserStoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserStoresRepository extends JpaRepository<UserStoresEntity, Long>, UserStoresRepositoryCustom {

    List<UserStoresEntity> findByUserStoreGroup_Id(Long storeId);

    @Modifying
    @Query("DELETE FROM UserStoresEntity s WHERE s.userStoreGroup.id = :groupId")
    void deleteByUserStoreGroupId(@Param("groupId") Long groupId);

    @Modifying
    @Query("DELETE FROM UserStoresEntity s WHERE s.userStoreGroup.id =: groupId AND s.stores.id = :storesId")
    void deleteByUserStoresIdAndUserStoreGroupId(@Param("groupId") Long groupId, @Param("storesId") Long storesId);

    Optional<UserStoresEntity> findByUserStoreGroup_IdAndStores_Id(Long groupId, Long storesId);
}
