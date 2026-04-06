package com.jos.ant.repository.mssql.mapper;

import com.jos.ant.common.payload.PersonPayload;
import com.jos.ant.repository.mssql.entity.PersonEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper( componentModel = "spring" )
public interface PersonMapper
{
    @Mapping( source = "personId", target = "personId" )
    @Mapping( source = "name", target = "name" )
    @Mapping( source = "lastName", target = "lastName" )
    @Mapping( source = "lastNameMother", target = "lastNameMother" )
    @Mapping( source = "mail", target = "mail" )
    @Mapping( source = "telephone", target = "telephone" )
    @Mapping( source = "birthday", target = "birthday", dateFormat = "yyyy-MM-dd" )
    @Mapping( source = "createdBy", target = "createdBy" )
    @Mapping( source = "createdDate", target = "createdDate" )
    @Mapping( source = "lastModifiedBy", target = "lastModifiedBy" )
    @Mapping( source = "lastModifiedDate", target = "lastModifiedDate" )
    PersonPayload toPersonPayload( PersonEntity persona );
    List<PersonPayload> toPersonPayloadList( List<PersonEntity> personas );

    @InheritInverseConfiguration
    PersonEntity toPersonEntity( PersonPayload person );
}