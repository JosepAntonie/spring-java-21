package com.jos.ant.repository.mssql.runner;

import com.jos.ant.repository.mssql.entity.PersonEntity;
import com.jos.ant.repository.mssql.entity.RoleEntity;
import com.jos.ant.repository.mssql.entity.UserEntity;
import com.jos.ant.repository.mssql.jpa.RoleJpa;
import com.jos.ant.repository.mssql.jpa.UserJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class StartCommandLineRunner implements CommandLineRunner
{
    private final UserJpa userJpa;
    private final RoleJpa roleJpa;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value( "${user.passphrase}" )
    private String passphrase;

    public void run( String @NonNull ... args )
    {
        log.info( "StartCommandLineRunner -> Run" );

        List<RoleEntity> startRoleEntityList = List.of( getRoleEntity( "God", "User God" ), getRoleEntity( "SuperAdmin", "Super Administrador" ), getRoleEntity( "Admin", "Administrador" ), getRoleEntity( "User", "Usuario" ) );
        List<RoleEntity> roleEntityList = roleJpa.findAll();

        if (  roleEntityList.isEmpty() ) roleJpa.saveAll( startRoleEntityList );
        else
        {
            Set<RoleKey> existingReleKeys = roleEntityList.stream().map( roleEntity -> new RoleKey( roleEntity.getCode(), roleEntity.getDescription() ) ).collect( Collectors.toSet() );
            List<RoleEntity> rolesToSave = startRoleEntityList.stream().filter( startRoleEntity -> !existingReleKeys.contains( new RoleKey( startRoleEntity.getCode(), startRoleEntity.getDescription() ) ) ).toList();
            if ( !rolesToSave.isEmpty() ) roleJpa.saveAll( rolesToSave );
        }

        List<UserEntity> users = userJpa.findAll();
        if ( users.isEmpty() ) userJpa.save( getUser() );
    }

    record RoleKey( String code, String description ) {}
    private RoleEntity getRoleEntity( String code, String description )
    {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode( code );
        roleEntity.setDescription( description );
        return roleEntity;
    }

    private UserEntity getUser()
    {
        UserEntity user = new UserEntity();
        user.setUsername( "JosepAntonie" );
        user.setPassphrase( passwordEncoder.encode( passphrase ) );
        user.setActive( Boolean.TRUE );
        user.setPerson( getPerson() );
        user.setRoles( roleJpa.findAll() );
        return user;
    }
    private PersonEntity getPerson()
    {
        PersonEntity person = new PersonEntity();
        person.setName( "Jose Antonio" );
        person.setLastName( "Gonzalez" );
        person.setLastNameMother( "Mendoza" );
        person.setBirthday( LocalDate.of( 1995, 10, 9 ) );
        person.setMail( "insomniantonio@icloud.com" );
        person.setTelephone( "5537552486" );
        return person;
    }
}
