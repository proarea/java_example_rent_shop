package com.snowboard_rental_crm.shared_data.model;

import com.snowboard_rental_crm.shared_data.enumeration.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntityAudit {

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "created_user_type")
    private UserType createdUserType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "updated_user_type")
    private UserType updatedUserType;

    @Column(name = "is_deleted")
    private boolean deleted;

    public void delete(Long userId, UserType userType) {
        this.deleted = true;
        this.updatedBy = userId;
        this.updatedUserType = userType;
    }
}
