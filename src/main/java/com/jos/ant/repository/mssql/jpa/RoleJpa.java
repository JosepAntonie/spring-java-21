package com.jos.ant.repository.mssql.jpa;

import com.jos.ant.repository.mssql.entity.RoleEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RoleJpa extends CatalogNoJpa<RoleEntity, Long>, QuerydslPredicateExecutor<RoleEntity>
{
}
