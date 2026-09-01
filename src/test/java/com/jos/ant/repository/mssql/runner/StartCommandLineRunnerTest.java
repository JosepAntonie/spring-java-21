package com.jos.ant.repository.mssql.runner;

import com.jos.ant.repository.mssql.entity.RoleEntity;
import com.jos.ant.repository.mssql.entity.UserEntity;
import com.jos.ant.repository.mssql.jpa.RoleJpa;
import com.jos.ant.repository.mssql.jpa.UserJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class StartCommandLineRunnerTest
{
    private StartCommandLineRunner startCommandLineRunner;
    private UserJpa userJpa;
    private RoleJpa roleJpa;

    @BeforeEach
    void setUp()
    {
        roleJpa = mock( RoleJpa.class );
        userJpa = mock( UserJpa.class );
        startCommandLineRunner = new StartCommandLineRunner( userJpa, roleJpa );
        ReflectionTestUtils.setField( startCommandLineRunner, "passphrase", "951009" );
    }

    @Test
    void run()
    {
        when( roleJpa.findAll() ).thenReturn( Collections.emptyList() );
        when( userJpa.findAll() ).thenReturn( Collections.emptyList() );
        startCommandLineRunner.run();

        List<RoleEntity> roleEntityList = new ArrayList<>( List.of( getRoleEntity( "God", "User God" ), getRoleEntity( "SuperAdmin", "Super Administrador" ) ) );
        when( roleJpa.findAll() ).thenReturn( roleEntityList );
        when( userJpa.findAll() ).thenReturn( Collections.singletonList( mock( UserEntity.class ) ) );
        startCommandLineRunner.run();

        roleEntityList.addAll( List.of( getRoleEntity( "Admin", "Administrador" ), getRoleEntity( "User", "Usuario" ) ) );
        when( roleJpa.findAll() ).thenReturn( roleEntityList );
        startCommandLineRunner.run();
        verify( userJpa, times( 3 ) ).findAll();
        verify( roleJpa, times( 4 ) ).findAll();
    }

    private RoleEntity getRoleEntity( String code, String description )
    {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode( code );
        roleEntity.setDescription( description );
        return roleEntity;
    }
}
