package com.jos.ant.batch.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class BirthdayScheduled
{
    private final JobOperator jobOperator;
    private final Job birthdayBatchJob;

    public void start()
    {
        log.info( "{} -> start", getClass().getSimpleName() );
        try
        {
            jobOperator.start( birthdayBatchJob, new JobParametersBuilder().addLong( "runTime", System.currentTimeMillis() ).toJobParameters() );
        }
        catch (Exception e)
        {
            log.error( "Error al ejecutar BirthdayBatchJob", e );
        }
    }
}
