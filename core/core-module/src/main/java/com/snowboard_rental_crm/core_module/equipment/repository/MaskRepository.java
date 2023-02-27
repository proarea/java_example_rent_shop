package com.snowboard_rental_crm.core_module.equipment.repository;

import com.snowboard_rental_crm.core_data.enumiration.equipment.LensTint;
import com.snowboard_rental_crm.core_module.equipment.entity.MaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaskRepository extends JpaRepository<MaskEntity, Long> {

    boolean existsMaskEntitiesByNameIgnoreCase(String name);

    MaskEntity findMaskEntityById(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM mask " +
            "WHERE (:lensTint is null or mask.lens_tint = cast(:lensTint AS text)) " +
            "and (:antiFog is null or mask.is_anti_fog = cast(:antiFog AS boolean)) " +
            "and (:name is null or upper(mask.name) like concat('%', upper(:name), '%'))" +
            "and mask.is_deleted = false")
    List<MaskEntity> findAllByFilter(
            String name,
            LensTint lensTint,
            Boolean antiFog
    );
}
