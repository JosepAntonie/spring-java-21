package com.jos.ant.repository.mssql.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CatalogNoJpa<T, I> extends JpaRepository<T, I>
{
}
