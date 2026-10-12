package com.jos.ant.service;

import com.jos.ant.common.exception.ResponseException;
import com.jos.ant.common.payload.request.BatchTaskRequest;
import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.response.BatchTaskResponse;
import com.jos.ant.repository.mysql.BatchTaskRepository;
import com.jos.ant.service.impl.BatchTaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BatchTaskServiceTest
{
    private BatchTaskService batchTaskService;
    private BatchTaskRepository batchTaskRepository;

    @BeforeEach
    void setup()
    {
        batchTaskRepository = mock( BatchTaskRepository.class );
        batchTaskService = new BatchTaskServiceImpl( batchTaskRepository );
    }

    @Test
    void findAll()
    {
        when( batchTaskRepository.findAll() ).thenReturn( Collections.emptyList() );
        assertNotNull( batchTaskService.findAll() );
    }

    @Test
    void findAllByFilter()
    {
        when( batchTaskRepository.findAllByFilter( any() ) ).thenReturn( new PageImpl<>( Collections.emptyList() ) );
        assertNotNull( batchTaskService.findAllByFilter( new FilterRequest<>( 0, 10, null, mock( BatchTaskRequest.class ) ) ) );
    }

    @Test
    void findById()
    {
        when( batchTaskRepository.findById( anyLong() ) ).thenReturn( Optional.empty() );
        assertNotNull( batchTaskService.findById( 1L ) );
    }

    @Test
    void findByTaskName()
    {
        when( batchTaskRepository.findByTaskName( anyString() ) ).thenReturn( Optional.empty() );
        assertNotNull( batchTaskService.findByTaskName( "test" ) );
    }

    @Test
    void save()
    {
        when( batchTaskService.save( any( BatchTaskRequest.class ) ) ).thenReturn( mock( BatchTaskResponse.class ) );
    }

    @Test
    void update()
    {
        when( batchTaskRepository.findById( anyLong() ) ).thenReturn( Optional.empty() );
        Throwable throwable = assertThrows( ResponseException.class, () -> batchTaskService.update( getBatchTaskRequest() ) );
        assertNotNull( throwable.getMessage() );

        when( batchTaskRepository.findById( anyLong() ) ).thenReturn( Optional.of( mock( BatchTaskResponse.class ) ) );
        when( batchTaskRepository.save( any( BatchTaskRequest.class ) ) ).thenReturn( mock( BatchTaskResponse.class ) );
        assertNotNull( batchTaskService.update( getBatchTaskRequest() ) );
    }

    @Test
    void deleteById()
    {
        when( batchTaskRepository.findById( anyLong() ) ).thenReturn( Optional.empty() );
        assertFalse( batchTaskService.deleteById( 1L ) );

        when( batchTaskRepository.findById( anyLong() ) ).thenReturn( Optional.of( mock( BatchTaskResponse.class ) ) );
        assertTrue( batchTaskService.deleteById( 1L ) );
    }

    private BatchTaskRequest getBatchTaskRequest()
    {
        return new BatchTaskRequest( 0L, "taskName", "cron", Boolean.TRUE );
    }
}
