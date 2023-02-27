package com.snowboard_rental_crm.core_module.equipment.entity;

import com.snowboard_rental_crm.core_data.enumiration.equipment.LensTint;
import com.snowboard_rental_crm.shared_data.model.BaseEntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "mask")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "is_deleted=false")
public class MaskEntity extends BaseEntityAudit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "photo_url")
    private String photoUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "lens_tint")
    private LensTint lensTint;

    @Column(name = "is_anti_fog")
    private boolean antiFog;

    @Column(name = "collateral_value")
    private BigDecimal collateralValue;

    @Column(name = "is_rental")
    private boolean rental;

}
