package com.snowboard_rental_crm.core_module.rental.entity;

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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "rental")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "is_deleted=false")
public class RentalEntity extends BaseEntityAudit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "snowboard_id")
    private Long snowboardId;

    @Column(name = "mask_id")
    private Long maskId;

    @Column(name = "document_info")
    private String documentInfo;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "collateral_value")
    private BigDecimal collateralValue;

}
