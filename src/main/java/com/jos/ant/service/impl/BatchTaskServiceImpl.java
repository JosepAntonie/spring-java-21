package com.jos.ant.service.impl;

import com.jos.ant.common.exception.ResponseException;
import com.jos.ant.common.payload.request.BatchTaskRequest;
import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.response.BatchTaskResponse;
import com.jos.ant.repository.mysql.BatchTaskRepository;
import com.jos.ant.service.BatchTaskService;
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
public class BatchTaskServiceImpl implements BatchTaskService
{
    private final BatchTaskRepository batchTaskRepository;

    public List<BatchTaskResponse> findAll()
    {
        log.info( "{} -> findAll", getClass().getSimpleName() );
        return batchTaskRepository.findAll();
    }

    public Page<BatchTaskResponse> findAllByFilter( FilterRequest<BatchTaskRequest> filterRequest )
    {
        log.info( "{} -> findAllByFilter", getClass().getSimpleName() );
        return batchTaskRepository.findAllByFilter( filterRequest );
    }

    public Optional<BatchTaskResponse> findById( Long taskId )
    {
        log.info( "{} -> findById", getClass().getSimpleName() );
        return batchTaskRepository.findById( taskId );
    }

    public Optional<BatchTaskResponse> findByTaskName( String taskName )
    {
        log.info( "{} -> findByTaskName", getClass().getSimpleName() );
        return  batchTaskRepository.findByTaskName( taskName );
    }

    @Transactional
    public BatchTaskResponse save( BatchTaskRequest batchTaskRequest )
    {
        log.info( "{} -> save", getClass().getSimpleName() );
        return batchTaskRepository.save( batchTaskRequest );
    }

    @Transactional
    public BatchTaskResponse update( BatchTaskRequest batchTaskRequest )
    {
        log.info( "{} -> update", getClass().getSimpleName() );
        return findById( batchTaskRequest.taskId() ).map( t -> batchTaskRepository.save( batchTaskRequest ) ).orElseThrow( () -> new ResponseException( HttpStatus.BAD_REQUEST, "", "", "" ) );
    }

    @Transactional
    public Boolean deleteById( Long taskId )
    {
        log.info( "{} -> deleteById", getClass().getSimpleName() );
        return findById( taskId ).map( t -> {
            batchTaskRepository.deleteById( taskId );
            return Boolean.TRUE;
        } ).orElse( Boolean.FALSE );
    }
}
