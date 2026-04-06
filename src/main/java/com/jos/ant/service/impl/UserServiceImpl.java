package com.jos.ant.service.impl;

import com.jos.ant.common.exception.ResponseException;
import com.jos.ant.common.payload.FilterPayload;
import com.jos.ant.common.payload.UserPayload;
import com.jos.ant.repository.mssql.UserRepository;
import com.jos.ant.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional( readOnly = true )
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    public List<UserPayload> findAll()
    {
        log.info( "UserService -> findAll" );
        return userRepository.findAll();
    }

    public Page<UserPayload> findAllByFilter( FilterPayload<UserPayload> filter )
    {
        log.info( "UserService -> findAllByFilter" );
        return userRepository.findAllByFilter( filter );
    }

    public Optional<UserPayload> findById( Long userId )
    {
        log.info( "UserService -> findById" );
        return userRepository.findById( userId );
    }

    @Transactional
    public UserPayload save( UserPayload userPayload )
    {
        log.info( "UserService -> save" );
        return userRepository.save( userPayload );
    }

    @Transactional
    public UserPayload update( UserPayload userPayload )
    {
        log.info( "UserService -> update" );
        return findById( userPayload.getUserId() ).map( u -> userRepository.save( userPayload ) ).orElseThrow( () -> new ResponseException( HttpStatus.BAD_REQUEST, "", "", null ) );
    }

    @Transactional
    public Boolean deleteById( Long userId )
    {
        log.info( "UserService -> deleteById" );
        return findById( userId ).map( u -> {
            userRepository.deleteById( userId );
            return Boolean.TRUE;
        } ).orElse( Boolean.FALSE );
    }
}
