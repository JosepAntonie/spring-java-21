package com.jos.ant.batch.reader;

import com.jos.ant.repository.mssql.entity.PersonEntity;
import com.jos.ant.repository.mssql.jpa.PersonJpa;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader;
import org.springframework.batch.infrastructure.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Log4j2
@Configuration
public class BirthdayItemReader
{
    @Bean
    public RepositoryItemReader<PersonEntity> birthdayPersonItemReader( PersonJpa personJpa )
    {
        log.info( "{} -> birthdayPersonItemReader", getClass().getSimpleName() );
        LocalDate today = LocalDate.now();
        return new RepositoryItemReaderBuilder<PersonEntity>()
                .name( "birthdayPersonItemReader" )
                .repository( personJpa )
                .methodName( "findAllBirthdayByToday" )
                .arguments( List.of( today.getMonthValue(), today.getDayOfMonth() ) )
                .pageSize( 50 )
                .sorts( Map.of( "personId", Sort.Direction.ASC ) )
                .build();
    }
}
