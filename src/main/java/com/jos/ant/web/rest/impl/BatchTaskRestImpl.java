package com.jos.ant.web.rest.impl;

import com.jos.ant.common.payload.request.BatchTaskRequest;
import com.jos.ant.common.payload.request.FilterRequest;
import com.jos.ant.common.payload.response.BatchTaskResponse;
import com.jos.ant.common.validation.groups.OnSave;
import com.jos.ant.common.validation.groups.OnUpdate;
import com.jos.ant.service.BatchTaskService;
import com.jos.ant.web.rest.BatchTaskRest;
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
@RequiredArgsConstructor
@RequestMapping( "/api/batch/task" )
@Tag( name = "BatchTask", description = "Batch Task Restful Api" )
public class BatchTaskRestImpl implements BatchTaskRest
{
    private final BatchTaskService batchTaskService;

    @GetMapping
    @Operation( summary = "1. FindAll" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<List<BatchTaskResponse>> findAll()
    {
        log.info( "{} -> findAll", getClass().getSimpleName() );
        return ResponseEntity.status( HttpStatus.OK ).body( batchTaskService.findAll() );
    }

    @PostMapping( "/filter" )
    @Operation( summary = "2. FindAllByFilter" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK " ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<Page<BatchTaskResponse>> findAllByFilter( @Valid @RequestBody FilterRequest<BatchTaskRequest> filterRequest )
    {
        log.info( "{} -> findAllByFilter", getClass().getSimpleName() );
        return ResponseEntity.status( HttpStatus.OK ).body( batchTaskService.findAllByFilter( filterRequest ) );
    }

    @GetMapping( "/{taskId:\\d+}" )
    @Operation( summary = "3. FindById" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<Optional<BatchTaskResponse>> findById( @PathVariable Long taskId )
    {
        log.info( "{} -> findById", getClass().getSimpleName() );
        return ResponseEntity.status( HttpStatus.OK ).body( batchTaskService.findById( taskId ) );
    }

    @GetMapping( "/{taskName:[a-zA-Z0-9_-]+}" )
    @Operation( summary = "4. FindByTaskName" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<Optional<BatchTaskResponse>> findByTaskName( @PathVariable String taskName )
    {
        log.info( "{} -> findByTaskName", getClass().getSimpleName() );
        return ResponseEntity.status( HttpStatus.OK ).body( batchTaskService.findByTaskName( taskName ) );
    }

    @PostMapping
    @Operation( summary = "5. Save" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<BatchTaskResponse> save( @Validated( OnSave.class ) @RequestBody BatchTaskRequest batchTaskRequest )
    {
        log.info( "{} -> save", getClass().getSimpleName() );
        return ResponseEntity.status( HttpStatus.OK ).body( batchTaskService.save( batchTaskRequest ) );
    }

    @PutMapping
    @Operation( summary = "6. Update" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<BatchTaskResponse> update( @Validated( OnUpdate.class ) @RequestBody BatchTaskRequest batchTaskRequest )
    {
        log.info( "{} -> update", getClass().getSimpleName() );
        return ResponseEntity.status( HttpStatus.OK ).body( batchTaskService.update( batchTaskRequest ) );
    }

    @DeleteMapping( "/{taskId}" )
    @Operation( summary = "7. DeleteById" )
    @ApiResponses( value = { @ApiResponse( responseCode = "200", description = "OK" ), @ApiResponse( responseCode = "400", description = "Bad Request" ) } )
    public ResponseEntity<Boolean> deleteById( @PathVariable Long taskId )
    {
        log.info( "{} -> deleteById", getClass().getSimpleName() );
        return ResponseEntity.status( HttpStatus.OK ).body( batchTaskService.deleteById( taskId ) );
    }
}