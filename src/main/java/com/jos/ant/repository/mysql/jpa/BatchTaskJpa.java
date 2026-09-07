package com.jos.ant.repository.mysql.jpa;

import com.jos.ant.repository.mysql.entity.BatchTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface BatchTaskJpa extends JpaRepository<BatchTaskEntity, Long>, QuerydslPredicateExecutor<BatchTaskEntity>
{
    Optional<BatchTaskEntity> findByTaskName( String taskName );
}
