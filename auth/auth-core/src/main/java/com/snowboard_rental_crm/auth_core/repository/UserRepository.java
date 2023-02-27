package com.snowboard_rental_crm.auth_core.repository;

import com.snowboard_rental_crm.auth_core.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByLoginOrPhoneOrEmail(String login, String phone, String email);

}
