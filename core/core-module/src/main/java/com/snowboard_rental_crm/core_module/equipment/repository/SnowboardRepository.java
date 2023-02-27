package com.snowboard_rental_crm.core_module.equipment.repository;

import com.snowboard_rental_crm.core_data.enumiration.EquipmentClass;
import com.snowboard_rental_crm.core_data.enumiration.snowboard.SnowboardStyle;
import com.snowboard_rental_crm.core_data.enumiration.snowboard.SnowboardType;
import com.snowboard_rental_crm.core_module.equipment.entity.SnowboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SnowboardRepository extends JpaRepository<SnowboardEntity, Long> {
    boolean existsSnowboardEntitiesByNameIgnoreCase(String name);

    SnowboardEntity findSnowboardEntityById(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM snowboard " +
            "WHERE (:equipmentClass is null or snowboard.equipment_class = cast(:equipmentClass AS text)) " +
            "and (:snowboardType is null or snowboard.snowboard_type = cast(:snowboardType AS text)) " +
            "and (:snowboardStyle is null or snowboard.snowboard_style = cast(:snowboardStyle AS text)) " +
            "and (:name is null or upper(snowboard.name) like concat('%', upper(:name), '%'))" +
            "and snowboard.is_deleted = false")
    List<SnowboardEntity> findAllByFilter(
            String name,
            EquipmentClass equipmentClass,
            SnowboardType snowboardType,
            SnowboardStyle snowboardStyle
    );
}
