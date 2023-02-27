package com.snowboard_rental_crm.auth_core.repository;


import com.snowboard_rental_crm.auth_core.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByUserId(Long userId);
}
