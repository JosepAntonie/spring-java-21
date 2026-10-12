package com.jos.ant.repository.mssql.jpa;

import com.jos.ant.repository.mssql.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PersonJpa extends JpaRepository<PersonEntity, Long>, QuerydslPredicateExecutor<PersonJpa>
{
    @Query( "SELECT p FROM PersonEntity p WHERE p.active IS TRUE AND MONTH( p.birthday ) = :month AND DAY( p.birthday ) = :day" )
    Page<PersonEntity> findAllBirthdayByToday( Integer month, Integer day, Pageable pageable );
}