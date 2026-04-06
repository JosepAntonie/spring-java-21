package com.jos.ant.web.rest.impl;

import com.jos.ant.common.payload.FilterPayload;
import com.jos.ant.common.payload.UserPayload;
import com.jos.ant.common.validation.groups.OnSave;
import com.jos.ant.common.validation.groups.OnUpdate;
import com.jos.ant.web.rest.UserRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Log4j2
@RestController
@RequestMapping( "/api/user" )
@Tag( name = "User", description = "User Restful Api" )
public class UserRestImpl implements UserRest
{
    @GetMapping
    @Operation( summary = "1. FindAll" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<List<UserPayload>> findAll()
    {
        log.info( "UserRest -> findAll" );
        return ResponseEntity.status( HttpStatus.OK ).body( Collections.emptyList() );
    }

    @PostMapping( "/filter" )
    @Operation( summary = "2. FindAllByFilter" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<List<UserPayload>> findAllByFilter( @Valid @RequestBody FilterPayload<UserPayload> filter )
    {
        log.info( "UserRest -> findAllByFilter" );
        return ResponseEntity.status( HttpStatus.OK ).body( Collections.emptyList() );
    }

    @GetMapping( "/{userId}" )
    @Operation( summary = "3. FindById" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<UserPayload> findById( @PathVariable Long userId )
    {
        log.info( "UserRest -> findById -> {}", userId );
        return ResponseEntity.status( HttpStatus.OK ).build();
    }

    @PostMapping
    @Operation( summary = "4. Save" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<Void> save( @Validated( OnSave.class ) @RequestBody UserPayload user )
    {
        log.info( "UserRest -> save" );
        return ResponseEntity.status( HttpStatus.OK ).build();
    }

    @PutMapping
    @Operation( summary = "5. Update" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<Void> update( @Validated( OnUpdate.class ) @RequestBody UserPayload user )
    {
        log.info( "UserRest -> update" );
        return ResponseEntity.status( HttpStatus.OK ).build();
    }

    @DeleteMapping( "/{userId}" )
    @Operation( summary = "6. DeleteById" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<Void> deleteById( @PathVariable Long userId )
    {
        log.info( "UserRest -> deleteById: {}", userId );
        return ResponseEntity.status( HttpStatus.OK ).build();
    }
}
