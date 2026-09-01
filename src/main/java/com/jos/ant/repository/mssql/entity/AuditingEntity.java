package com.jos.ant.repository.mssql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Setter
@Getter
@MappedSuperclass
@EntityListeners( AuditingEntityListener.class )
public abstract class AuditingEntity<U>
{
    @CreatedBy
    @Column( name = "DX_CREATE_BY" )
    protected U createdBy;

    @CreatedDate
    @Column( name = "DD_CREATE_DATE", columnDefinition = "datetime" )
    private Instant createdDate;

    @LastModifiedBy
    @Column( name = "DX_LAST_MODIFIED_BY" )
    protected U lastModifiedBy;

    @LastModifiedDate
    @Column( name = "DD_LAST_MODIFIED_DATE", columnDefinition = "datetime" )
    private Instant lastModifiedDate;
}