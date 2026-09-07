package com.jos.ant.service;

import com.jos.ant.common.payload.request.BatchTaskRequest;
import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.response.BatchTaskResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BatchTaskService
{
    List<BatchTaskResponse> findAll();
    Page<BatchTaskResponse> findAllByFilter( FilterRequest<BatchTaskRequest> filterRequest );
    Optional<BatchTaskResponse> findById( Long taskId );
    Optional<BatchTaskResponse> findByTaskName( String taskName );
    BatchTaskResponse save( BatchTaskRequest batchTaskRequest );
    BatchTaskResponse update( BatchTaskRequest batchTaskRequest );
    Boolean deleteById( Long taskId );
}
