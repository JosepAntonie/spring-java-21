package com.jos.ant.repository.mssql.predicate;

import com.jos.ant.common.payload.request.UserRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserPredicateTest
{
    @Test
    void findAllByFilter()
    {
        UserRequest userRequest = mock( UserRequest.class );
        when( userRequest.active() ).thenReturn( null );
        assertNotNull( UserPredicate.findAllByFilter( userRequest, null ) );

        when( userRequest.username() ).thenReturn( "" );
        when( userRequest.active() ).thenReturn( Boolean.FALSE );

        assertNotNull( UserPredicate.findAllByFilter( userRequest, "" ) );

        when( userRequest.username() ).thenReturn( "username" );
        assertNotNull( UserPredicate.findAllByFilter( userRequest, "search" ) );
    }
}
