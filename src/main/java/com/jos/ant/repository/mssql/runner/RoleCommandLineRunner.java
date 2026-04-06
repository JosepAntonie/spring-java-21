package com.jos.ant.repository.mssql.runner;

import com.jos.ant.repository.mssql.entity.RoleEntity;
import com.jos.ant.repository.mssql.jpa.RoleJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Profile( "!test" )
@Component
@RequiredArgsConstructor
public class RoleCommandLineRunner implements CommandLineRunner
{
    private final RoleJpa roleJpa;

    public void run( String @NonNull ... args )
    {
        log.info( "RolCommandLineRunner -> Run" );
        List<RoleEntity> roleEntities = roleJpa.findAll();
        if ( roleEntities.isEmpty() )
        {
            roleJpa.saveAll( Arrays.asList( getRolGod(), getRolSuperAdmin(), getRolAdmin(), getRolUser() ) );
        }
    }

    private RoleEntity getRolGod()
    {
        RoleEntity role = new RoleEntity();
        role.setCode( "God" );
        role.setDescription( "User God" );
        return role;
    }

    private RoleEntity getRolSuperAdmin()
    {
        RoleEntity role = new RoleEntity();
        role.setCode( "SuperAdmin" );
        role.setDescription( "Super Administrador" );
        return role;
    }

    private RoleEntity getRolAdmin()
    {
        RoleEntity role = new RoleEntity();
        role.setCode( "Admin" );
        role.setDescription( "Administrador" );
        return role;
    }

    private RoleEntity getRolUser()
    {
        RoleEntity role = new RoleEntity();
        role.setCode( "User" );
        role.setDescription( "Usuario" );
        return role;
    }
}