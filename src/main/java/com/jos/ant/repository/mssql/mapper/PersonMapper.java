package com.jos.ant.repository.mssql.mapper;

import com.jos.ant.common.payload.request.PersonRequest;
import com.jos.ant.common.payload.response.PersonResponse;
import com.jos.ant.repository.mssql.entity.PersonEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper( componentModel = "spring" )
public interface PersonMapper
{
    PersonEntity toPersonEntity( PersonRequest personRequest );
    PersonResponse toPersonResponse(PersonEntity personEntity );
    List<PersonResponse> toPersonResponseList( List<PersonEntity> personEntityList );
}