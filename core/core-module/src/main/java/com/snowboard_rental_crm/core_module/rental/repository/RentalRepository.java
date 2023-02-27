package com.snowboard_rental_crm.core_module.rental.repository;

import com.snowboard_rental_crm.core_module.rental.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
    boolean existsBySnowboardIdAndActiveIsTrue(Long id);

    boolean existsByMaskIdAndActiveIsTrue(Long id);


}
