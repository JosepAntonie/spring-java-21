package com.jos.ant.batch.job;

import com.jos.ant.common.model.BirthdayNotification;
import com.jos.ant.repository.mssql.entity.PersonEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class BirthdayBatchJob
{
    private final JobRepository jobRepository;
    private final PlatformTransactionManager mssqlTransactionManager;

    @Bean
    public Job birthdayJob( Step birthdayStep )
    {
        log.info( "{} -> birthdayJob", getClass().getSimpleName() );
        return new JobBuilder( "BirthdayJob", jobRepository ).start( birthdayStep ).build();
    }

    @Bean
    public Step birthdayStep( RepositoryItemReader<PersonEntity> birthdayPersonItemReader, ItemProcessor<PersonEntity, BirthdayNotification> birthdayItemProcessor, ItemWriter<BirthdayNotification> birthdayItemWriter )
    {
        log.info( "{} -> birthdayStep", getClass().getSimpleName() );
        return new StepBuilder( "birthdayStep", jobRepository )
                .<PersonEntity, BirthdayNotification>chunk( 50 )
                .transactionManager( mssqlTransactionManager )
                .reader( birthdayPersonItemReader )
                .processor( birthdayItemProcessor )
                .writer( birthdayItemWriter )
                .build();
    }
}
