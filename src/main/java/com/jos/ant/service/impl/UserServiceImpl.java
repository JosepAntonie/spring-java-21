package com.jos.ant.service.impl;

import com.jos.ant.common.exception.ResponseException;
import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.request.UserRequest;
import com.jos.ant.common.payload.response.UserResponse;
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

    public List<UserResponse> findAll()
    {
        log.info( "UserService -> findAll" );
        return userRepository.findAll();
    }

    public Page<UserResponse> findAllByFilter( FilterRequest<UserRequest> filterRequest )
    {
        log.info( "UserService -> findAllByFilter" );
        return userRepository.findAllByFilter( filterRequest );
    }

    public Optional<UserResponse> findById( Long userId )
    {
        log.info( "UserService -> findById" );
        return userRepository.findById( userId );
    }

    @Transactional
    public UserResponse save( UserRequest userRequest )
    {
        log.info( "UserService -> save" );
        return userRepository.save( userRequest );
    }

    @Transactional
    public UserResponse update( UserRequest userRequest )
    {
        log.info( "UserService -> update" );
        return findById( userRequest.userId() ).map( u -> userRepository.save( userRequest ) ).orElseThrow( () -> new ResponseException( HttpStatus.BAD_REQUEST, "", "", null ) );
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
