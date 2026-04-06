package com.jos.ant.repository.mssql.jpa;

import com.jos.ant.repository.mssql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserJpa extends JpaRepository<UserEntity, Long>, QuerydslPredicateExecutor<UserEntity>
{
}
