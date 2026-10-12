package com.jos.ant.repository.mysql;

import com.jos.ant.common.payload.request.BatchTaskRequest;
import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.response.BatchTaskResponse;
import com.jos.ant.repository.mysql.entity.BatchTaskEntity;
import com.jos.ant.repository.mysql.impl.BatchTaskRepositoryImpl;
import com.jos.ant.repository.mysql.jpa.BatchTaskJpa;
import com.jos.ant.repository.mysql.mapper.BatchTaskMapper;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BatchTaskRepositoryTest
{
    private BatchTaskRepository batchTaskRepository;
    private BatchTaskJpa batchTaskJpa;
    private BatchTaskMapper batchTaskMapper;

    @BeforeEach
    void setUp()
    {
        batchTaskMapper = mock( BatchTaskMapper.class );
        batchTaskJpa = mock( BatchTaskJpa.class );
        batchTaskRepository = new BatchTaskRepositoryImpl( batchTaskJpa, batchTaskMapper );
    }

    @Test
    void findAll()
    {
        when( batchTaskMapper.toBatchTaskResponseList( anyList() ) ).thenReturn( Collections.emptyList() );
        when( batchTaskJpa.findAll() ).thenReturn( Collections.emptyList() );
        assertNotNull( batchTaskRepository.findAll() );
    }

    @Test
    void findAllByFilter()
    {
        when( batchTaskMapper.toBatchTaskResponseList( anyList() ) ).thenReturn( Collections.emptyList() );
        when( batchTaskJpa.findAll( any( BooleanBuilder.class ), any( PageRequest.class ) ) ).thenReturn( new PageImpl<>( Collections.emptyList() ) );
        assertNotNull( batchTaskRepository.findAllByFilter( getFilterPayload() ) );
    }
    private FilterRequest<BatchTaskRequest> getFilterPayload()
    {
        return new FilterRequest<>( 0, 10, null, mock( BatchTaskRequest.class ) );
    }

    @Test
    void findById()
    {
        when( batchTaskMapper.toBatchTaskResponse( any() ) ).thenReturn( mock( BatchTaskResponse.class ) );
        when( batchTaskJpa.findById( anyLong() ) ).thenReturn( Optional.of( new BatchTaskEntity() ) );
        assertNotNull( batchTaskRepository.findById( 1L ) );
    }

    @Test
    void findByTaskName()
    {
        when( batchTaskMapper.toBatchTaskResponse( any() ) ).thenReturn( mock( BatchTaskResponse.class ) );
        when( batchTaskJpa.findByTaskName( anyString() ) ).thenReturn( Optional.of( new BatchTaskEntity() ) );
        assertNotNull( batchTaskRepository.findByTaskName( "task" ) );
    }

    @Test
    void save()
    {
        when( batchTaskMapper.toBatchTaskEntity( any( BatchTaskRequest.class ) ) ).thenReturn( new BatchTaskEntity() );
        when( batchTaskJpa.save( any( BatchTaskEntity.class ) ) ).thenReturn( new BatchTaskEntity() );
        when( batchTaskMapper.toBatchTaskResponse( any( BatchTaskEntity.class ) ) ).thenReturn( mock( BatchTaskResponse.class ) );
        assertNotNull( batchTaskRepository.save( mock( BatchTaskRequest.class ) ) );
    }

    @Test
    void deleteById()
    {
        batchTaskRepository.deleteById( 1L );
        verify( batchTaskJpa, times( 1 ) ).deleteById( anyLong() );
    }
}
