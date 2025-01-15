package com.trazzz.configserver.model.entity;

import com.trazzz.configserver.model.enumeration.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "application_properties")
public final class ApplicationProperties {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "profile")
    private String profile;

    @Column(name = "label")
    private String label;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "createdAt")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifiedAt;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @PrePersist
    public void beforeSave(){
        this.createdAt = new Date();
        this.status = Objects.isNull(this.status) ? Status.ACTIVE : this.status;
    }

    @PreUpdate
    public void afterUpdate(){
        this.modifiedAt = new Date();
    }
}
