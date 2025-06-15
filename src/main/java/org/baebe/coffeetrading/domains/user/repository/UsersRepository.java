package org.baebe.coffeetrading.domains.user.repository;

import java.util.List;
import java.util.Optional;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByEmail(String userEmail);

    boolean existsByEmailEqualsIgnoreCase(String email);

    boolean existsByNickname(String nickname);

    List<UsersEntity> findByUserNameAndBirthDayAndPhone(String userName, String birthDay, String phone);
}
