package com.jos.ant.service;

import com.jos.ant.common.exception.ResponseException;
import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.request.PersonRequest;
import com.jos.ant.common.payload.request.RoleRequest;
import com.jos.ant.common.payload.request.UserRequest;
import com.jos.ant.common.payload.response.UserResponse;
import com.jos.ant.repository.mssql.UserRepository;
import com.jos.ant.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( MockitoExtension.class )
class UserServiceTest
{
    private UserService service;
    private UserRepository repository;

    @BeforeEach
    void setUp()
    {
        repository = mock( UserRepository.class );
        service = new UserServiceImpl( repository );
    }

    @Test
    void findAll()
    {
        when( repository.findAll() ).thenReturn( Collections.emptyList() );
        assertNotNull( service.findAll() );
    }

    @Test
    void findAllByFilter()
    {
        when( repository.findAllByFilter( any() ) ).thenReturn( new PageImpl<>( Collections.emptyList() ) );
        assertNotNull( service.findAllByFilter( new FilterRequest<>( 0, 10, null, mock( UserRequest.class ) ) ) );
    }

    @Test
    void findById()
    {
        when( repository.findById( anyLong() ) ).thenReturn( Optional.of( mock( UserResponse.class ) ) );
        assertNotNull( service.findById( 1L ) );
    }

    @Test
    void save()
    {
        when( repository.save( any() ) ).thenReturn( mock( UserResponse.class ) );
        assertNotNull( service.save( mock( UserRequest.class ) ) );
    }

    @Test
    void update()
    {
        UserRequest userRequest = getUserRequest();
        when(  repository.findById( anyLong() ) ).thenReturn( Optional.of( mock( UserResponse.class ) ) );
        when( repository.save( any() ) ).thenReturn( mock( UserResponse.class ) );
        assertNotNull( service.update( userRequest ) );

        when( repository.findById( anyLong() ) ).thenReturn( Optional.empty() );
        Throwable throwable = assertThrows( ResponseException.class, () -> service.update( userRequest ) );
        assertEquals( "", throwable.getMessage() );
    }
    private UserRequest getUserRequest()
    {
        return new UserRequest( 1L, "username", "passphrase", "code", Boolean.TRUE, mock( PersonRequest.class ), List.of( mock( RoleRequest.class ) ) );
    }

    @Test
    void deleteById()
    {
        when( repository.findById( anyLong() ) ).thenReturn( Optional.empty() );
        assertFalse( service.deleteById( 1L ) );

        when( repository.findById( anyLong() ) ).thenReturn( Optional.of( mock( UserResponse.class ) ) );
        assertTrue( service.deleteById( 1L ) );
    }
}
