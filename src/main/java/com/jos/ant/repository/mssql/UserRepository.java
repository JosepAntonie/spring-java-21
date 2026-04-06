package com.jos.ant.repository.mssql;

import com.jos.ant.common.payload.FilterPayload;
import com.jos.ant.common.payload.UserPayload;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserRepository
{
    List<UserPayload> findAll();
    Page<UserPayload> findAllByFilter( FilterPayload<UserPayload> filter );
    Optional<UserPayload> findById( Long userId );
    UserPayload save( UserPayload user );
    void deleteById( Long userId );
}
