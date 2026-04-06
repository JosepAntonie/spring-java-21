package com.jos.ant.repository.mssql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table( name = "MM_USER" )
public class UserEntity extends AuditingEntity<String>
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "SK_USER_ID" )
    private Long userId;

    @Column( name = "DX_USERNAME", nullable = false, unique = true )
    private String username;

    @Column( name = "DX_PASSPHRASE", nullable = false )
    private String passphrase;

    @Column( name = "DX_CODE" )
    private String code;

    @Column( name = "DN_ACTIVE", nullable = false )
    private Boolean active;

    @OneToOne( fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH } )
    @JoinColumn( name = "KN_PERSON_ID", referencedColumnName = "SK_PERSON_ID", nullable = false )
    private PersonEntity person;

    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable( name = "DD_USUARIO_ROL", joinColumns = @JoinColumn( name = "KN_USER_ID", referencedColumnName = "SK_USER_ID" ), inverseJoinColumns = @JoinColumn( name = "KN_ROL_ID", referencedColumnName = "SK_CATALOG_ID" ) )
    private List<RoleEntity> roles;
}
