package com.jos.ant.web.rest;

import com.jos.ant.common.payload.FilterPayload;
import com.jos.ant.common.payload.UserPayload;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRest
{
    ResponseEntity<List<UserPayload>> findAll();

    ResponseEntity<List<UserPayload>> findAllByFilter( FilterPayload<UserPayload> user );

    ResponseEntity<UserPayload> findById( Long userId );

    ResponseEntity<Void> save( UserPayload user );

    ResponseEntity<Void> update( UserPayload user );

    ResponseEntity<Void> deleteById( Long userId );
}
