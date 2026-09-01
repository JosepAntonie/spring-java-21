package com.jos.ant.service;

import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.request.UserRequest;
import com.jos.ant.common.payload.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService
{
    List<UserResponse> findAll();
    Page<UserResponse> findAllByFilter( FilterRequest<UserRequest> filterRequest );
    Optional<UserResponse> findById( Long userId );
    UserResponse save( UserRequest userRequest );
    UserResponse update( UserRequest userRequest );
    Boolean deleteById( Long userId );
}
