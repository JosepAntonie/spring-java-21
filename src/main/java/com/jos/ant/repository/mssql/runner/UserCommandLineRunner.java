package com.jos.ant.repository.mssql.runner;

import com.jos.ant.repository.mssql.entity.PersonEntity;
import com.jos.ant.repository.mssql.entity.UserEntity;
import com.jos.ant.repository.mssql.jpa.RoleJpa;
import com.jos.ant.repository.mssql.jpa.UserJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

@Log4j2
@Profile( "!test" )
@Component
@RequiredArgsConstructor
public class UserCommandLineRunner implements CommandLineRunner
{
    private final UserJpa userJpa;
    private final RoleJpa roleJpa;
    //private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void run( String @NonNull ... args )
    {
        log.info( "UserCommondLineRunner -> Run" );
        List<UserEntity> users = userJpa.findAll();
        if ( users.isEmpty() )
        {
            userJpa.save( getUser() );
        }
    }

    private UserEntity getUser()
    {
        UserEntity user = new UserEntity();
        user.setUsername( "JosepAntonie" );
        user.setPassphrase( "951009" );
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
        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.YEAR, 1995 );
        calendar.set( Calendar.MONTH, 9 );
        calendar.set( Calendar.DAY_OF_MONTH, 9 );
        person.setBirthday( calendar.getTime().toInstant().atZone( ZoneId.systemDefault() ).toLocalDate() );
        person.setMail( "insomniantonio@icloud.com" );
        person.setTelephone( "5537552486" );
        return person;
    }
}