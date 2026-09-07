package com.jos.ant.repository.mysql.impl;

import com.jos.ant.common.payload.request.BatchTaskRequest;
import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.response.BatchTaskResponse;
import com.jos.ant.repository.mysql.BatchTaskRepository;
import com.jos.ant.repository.mysql.entity.BatchTaskEntity;
import com.jos.ant.repository.mysql.jpa.BatchTaskJpa;
import com.jos.ant.repository.mysql.mapper.BatchTaskMapper;
import com.jos.ant.repository.mysql.predicate.BatchTaskPredicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class BatchTaskRepositoryImpl implements BatchTaskRepository
{
    private final BatchTaskJpa batchTaskJpa;
    private final BatchTaskMapper batchTaskMapper;

    public List<BatchTaskResponse> findAll()
    {
        log.info( "{} -> FindAll", getClass().getSimpleName() );
        return batchTaskMapper.toBatchTaskResponseList( batchTaskJpa.findAll() );
    }

    public Page<BatchTaskResponse> findAllByFilter( FilterRequest<BatchTaskRequest> filterRequest )
    {
        log.info( "{} -> FindAllByFilter", getClass().getSimpleName() );
        PageRequest pageRequest = PageRequest.of( filterRequest.pageNumber(), filterRequest.pageSize(), Sort.by( Sort.Order.asc( "taskId" ) ) );
        Page<BatchTaskEntity> batchTaskPage = batchTaskJpa.findAll( BatchTaskPredicate.findAllByFilter( filterRequest.request(), filterRequest.search() ), pageRequest );
        return new PageImpl<>( batchTaskMapper.toBatchTaskResponseList( batchTaskPage.getContent() ), pageRequest, batchTaskPage.getTotalElements() );
    }

    public Optional<BatchTaskResponse> findById( Long taskId )
    {
        log.info( "{} -> FindById", getClass().getSimpleName() );
        return Optional.ofNullable( batchTaskMapper.toBatchTaskResponse( batchTaskJpa.findById( taskId ).orElse( null ) ) );
    }

    public Optional<BatchTaskResponse> findByTaskName( String taskName )
    {
        log.info( "{} -> FindByBatchTaskName", getClass().getSimpleName() );
        return Optional.ofNullable( batchTaskMapper.toBatchTaskResponse( batchTaskJpa.findByTaskName( taskName ).orElse( null ) ) );
    }

    public BatchTaskResponse save( BatchTaskRequest batchTaskRequest )
    {
        log.info( "{} -> Save", getClass().getSimpleName() );
        return batchTaskMapper.toBatchTaskResponse( batchTaskJpa.save( batchTaskMapper.toBatchTaskEntity(  batchTaskRequest ) ) );
    }

    public void deleteById( Long taskId )
    {
        log.info( "{} -> DeleteById", getClass().getSimpleName() );
        batchTaskJpa.deleteById( taskId );
    }
}