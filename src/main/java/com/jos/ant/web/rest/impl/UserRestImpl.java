package com.jos.ant.web.rest.impl;

import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.request.UserRequest;
import com.jos.ant.common.payload.response.UserResponse;
import com.jos.ant.common.validation.groups.OnSave;
import com.jos.ant.common.validation.groups.OnUpdate;
import com.jos.ant.service.UserService;
import com.jos.ant.web.rest.UserRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping( "/api/user" )
@RequiredArgsConstructor
@Tag( name = "User", description = "User Restful Api" )
public class UserRestImpl implements UserRest
{
    private final UserService userService;

    @GetMapping
    @Operation( summary = "1. FindAll" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<List<UserResponse>> findAll()
    {
        log.info( "UserRest -> findAll" );
        return ResponseEntity.status( HttpStatus.OK ).body( userService.findAll() );
    }

    @PostMapping( "/filter" )
    @Operation( summary = "2. FindAllByFilter" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<Page<UserResponse>> findAllByFilter( @Valid @RequestBody FilterRequest<UserRequest> filterRequest )
    {
        log.info( "UserRest -> findAllByFilter" );
        return ResponseEntity.status( HttpStatus.OK ).body( userService.findAllByFilter( filterRequest ) );
    }

    @GetMapping( "/{userId}" )
    @Operation( summary = "3. FindById" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<Optional<UserResponse>> findById( @PathVariable Long userId )
    {
        log.info( "UserRest -> findById -> {}", userId );
        return ResponseEntity.status( HttpStatus.OK ).body( userService.findById( userId ) );
    }

    @PostMapping
    @Operation( summary = "4. Save" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<UserResponse> save( @Validated( OnSave.class ) @RequestBody UserRequest userRequest )
    {
        log.info( "UserRest -> save" );
        return ResponseEntity.status( HttpStatus.OK ).body( userService.save( userRequest ) );
    }

    @PutMapping
    @Operation( summary = "5. Update" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<UserResponse> update(@Validated( OnUpdate.class ) @RequestBody UserRequest userRequest )
    {
        log.info( "UserRest -> update" );
        return ResponseEntity.status( HttpStatus.OK ).body( userService.update( userRequest ) );
    }

    @DeleteMapping( "/{userId}" )
    @Operation( summary = "6. DeleteById" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<Boolean> deleteById( @PathVariable Long userId )
    {
        log.info( "UserRest -> deleteById" );
        return ResponseEntity.status( HttpStatus.OK ).body( userService.deleteById( userId ) );
    }
}
