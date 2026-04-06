package com.jos.ant.web.rest;

import com.jos.ant.common.payload.FilterPayload;
import com.jos.ant.common.payload.UserPayload;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserRest
{
    ResponseEntity<List<UserPayload>> findAll();

    ResponseEntity<Page<UserPayload>> findAllByFilter( FilterPayload<UserPayload> user );

    ResponseEntity<Optional<UserPayload>> findById( Long userId );

    ResponseEntity<UserPayload> save( UserPayload user );

    ResponseEntity<UserPayload> update( UserPayload user );

    ResponseEntity<Boolean> deleteById( Long userId );
}
