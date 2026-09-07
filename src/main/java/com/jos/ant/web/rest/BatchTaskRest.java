package com.jos.ant.web.rest;

import com.jos.ant.common.payload.request.BatchTaskRequest;
import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.response.BatchTaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BatchTaskRest
{
    ResponseEntity<List<BatchTaskResponse>> findAll();
    ResponseEntity<Page<BatchTaskResponse>> findAllByFilter( FilterRequest<BatchTaskRequest> filterRequest );
    ResponseEntity<Optional<BatchTaskResponse>> findById( Long taskId );
    ResponseEntity<Optional<BatchTaskResponse>> findByTaskName( String taskName );
    ResponseEntity<BatchTaskResponse> save( BatchTaskRequest batchTaskRequest );
    ResponseEntity<BatchTaskResponse> update( BatchTaskRequest batchTaskRequest );
    ResponseEntity<Boolean> deleteById( Long taskId );
}
