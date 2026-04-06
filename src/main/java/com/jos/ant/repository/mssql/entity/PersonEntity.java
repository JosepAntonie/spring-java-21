package com.jos.ant.repository.mssql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table( name = "DD_PERSON" )
public class PersonEntity extends AuditingEntity<String>
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "SK_PERSON_ID" )
    private Long personId;

    @Column( name = "DX_NAME", nullable = false )
    private String name;

    @Column( name = "DX_LAST_NAME", nullable = false )
    private String lastName;

    @Column( name = "DX_LAST_NAME_MOTHER" )
    private String lastNameMother;

    @Column( name = "DX_MAIL", nullable = false, unique = true )
    private String mail;

    @Column( name = "DX_TELEPHONE", nullable = false, unique = true )
    private String telephone;

    @Column( name = "DD_BIRTHDAY", columnDefinition = "date" )
    private LocalDate birthday;
}
