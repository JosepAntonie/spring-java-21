package com.jos.ant.repository.mssql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table( name = "CC_CATALOG" )
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( discriminatorType = DiscriminatorType.STRING, name = "DX_TYPE", length = 100 )
public abstract class CatalogEntity
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "SK_CATALOG_ID" )
    private Long catalogId;

    @Column( name = "DX_DESCRIPTION" )
    private String description;

    @Column( name = "DX_CODE" )
    private String code;
}