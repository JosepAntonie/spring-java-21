package com.jos.ant.repository.mysql.predicate;

import com.jos.ant.common.payload.request.BatchTaskRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BatchTaskPredicateTest
{
    @Test
    void findAllByFilter()
    {
        BatchTaskRequest batchTaskRequest = mock( BatchTaskRequest.class );
        when( batchTaskRequest.taskName() ).thenReturn( null );
        when( batchTaskRequest.cron() ).thenReturn( null );
        when( batchTaskRequest.active() ).thenReturn( null );

        assertNotNull( BatchTaskPredicate.findAllByFilter( batchTaskRequest, null ) );

        when( batchTaskRequest.taskName() ).thenReturn( "" );
        when( batchTaskRequest.cron() ).thenReturn( "" );
        when( batchTaskRequest.active() ).thenReturn( Boolean.FALSE );

        assertNotNull( BatchTaskPredicate.findAllByFilter( batchTaskRequest, "" ) );

        when( batchTaskRequest.taskName() ).thenReturn( "taskName" );
        when( batchTaskRequest.cron() ).thenReturn( "cron" );
        when( batchTaskRequest.active() ).thenReturn( Boolean.TRUE );

        assertNotNull( BatchTaskPredicate.findAllByFilter( batchTaskRequest, "search" ) );
    }
}
