package com.jos.ant.web.rest;

import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.request.UserRequest;
import com.jos.ant.common.payload.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserRest
{
    ResponseEntity<List<UserResponse>> findAll();

    ResponseEntity<Page<UserResponse>> findAllByFilter( FilterRequest<UserRequest> filterRequest );

    ResponseEntity<Optional<UserResponse>> findById( Long userId );

    ResponseEntity<UserResponse> save( UserRequest userRequest );

    ResponseEntity<UserResponse> update( UserRequest userRequest );

    ResponseEntity<Boolean> deleteById( Long userId );
}
