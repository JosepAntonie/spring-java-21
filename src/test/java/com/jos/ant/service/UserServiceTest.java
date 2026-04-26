package com.jos.ant.service;

import com.jos.ant.common.exception.ResponseException;
import com.jos.ant.common.payload.FilterPayload;
import com.jos.ant.common.payload.UserPayload;
import com.jos.ant.repository.mssql.UserRepository;
import com.jos.ant.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest( classes = UserService.class )
class UserServiceTest
{
    private UserService service;
    private UserRepository repository;

    @BeforeEach
    void setUp()
    {
        repository = Mockito.mock( UserRepository.class );
        service = new UserServiceImpl( repository );
    }

    @Test
    void findAll()
    {
        Mockito.when( repository.findAll() ).thenReturn( Collections.emptyList() );
        Assertions.assertNotNull( service.findAll() );
    }

    @Test
    void findAllByFilter()
    {
        Mockito.when( repository.findAllByFilter( Mockito.any() ) ).thenReturn( new PageImpl<>( Collections.emptyList() ) );
        Assertions.assertNotNull( service.findAllByFilter( new FilterPayload<>() ) );
    }

    @Test
    void findById()
    {
        Mockito.when( repository.findById( Mockito.anyLong() ) ).thenReturn( Optional.of( new UserPayload() ) );
        Assertions.assertNotNull( service.findById( 1L ) );
    }

    @Test
    void save()
    {
        Mockito.when( repository.save( Mockito.any() ) ).thenReturn( new UserPayload() );
        Assertions.assertNotNull( service.save( new UserPayload() ) );
    }

    @Test
    void update()
    {
        UserPayload userPayload = getUserPayload();
        Mockito.when(  repository.findById( Mockito.anyLong() ) ).thenReturn( Optional.of( new UserPayload() ) );
        Mockito.when( repository.save( Mockito.any() ) ).thenReturn( new UserPayload() );
        Assertions.assertNotNull( service.update( userPayload ) );

        Mockito.when( repository.findById( Mockito.anyLong() ) ).thenReturn( Optional.empty() );
        Throwable throwable = Assertions.assertThrows( ResponseException.class, () -> service.update( userPayload ) );
        Assertions.assertEquals( "", throwable.getMessage() );
    }
    private UserPayload getUserPayload()
    {
        UserPayload userPayload = new UserPayload();
        userPayload.setUserId( 1L );
        return userPayload;
    }

    @Test
    void deleteById()
    {
        Mockito.when( repository.findById( Mockito.anyLong() ) ).thenReturn( Optional.empty() );
        Assertions.assertFalse( service.deleteById( 1L ) );

        Mockito.when( repository.findById( Mockito.anyLong() ) ).thenReturn( Optional.of( new UserPayload() ) );
        Assertions.assertTrue( service.deleteById( 1L ) );
    }
}
