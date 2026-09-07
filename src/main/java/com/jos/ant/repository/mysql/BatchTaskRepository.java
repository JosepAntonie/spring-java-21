package com.jos.ant.repository.mysql;

import com.jos.ant.common.payload.request.BatchTaskRequest;
import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.response.BatchTaskResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BatchTaskRepository
{
    List<BatchTaskResponse> findAll();
    Page<BatchTaskResponse> findAllByFilter( FilterRequest<BatchTaskRequest> filterRequest );
    Optional<BatchTaskResponse> findById( Long taskId );
    Optional<BatchTaskResponse> findByTaskName( String taskName );
    BatchTaskResponse save( BatchTaskRequest batchTaskRequest );
    void deleteById( Long taskId );
}
