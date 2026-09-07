package com.jos.ant.repository.mysql.mapper;

import com.jos.ant.common.payload.request.BatchTaskRequest;
import com.jos.ant.common.payload.response.BatchTaskResponse;
import com.jos.ant.repository.mysql.entity.BatchTaskEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper( componentModel = "spring" )
public interface BatchTaskMapper
{
    BatchTaskEntity toBatchTaskEntity( BatchTaskRequest batchTaskRequest );
    BatchTaskResponse toBatchTaskResponse( BatchTaskEntity batchTaskEntity );
    List<BatchTaskResponse> toBatchTaskResponseList( List<BatchTaskEntity> batchTaskEntityList );
}
