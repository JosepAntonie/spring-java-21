package com.jos.ant.scheduled;

import com.jos.ant.common.exception.ApplicationException;
import com.jos.ant.common.payload.response.BatchTaskResponse;
import com.jos.ant.service.BatchTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.javacrumbs.shedlock.core.DefaultLockingTaskExecutor;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Log4j2
@Component
@RequiredArgsConstructor
public class BatchTaskScheduled
{
    private final BatchTaskService batchTaskService;
    private final LockProvider lockProvider;
    private final JobOperator jobOperator;
    private final Clock clock;
    private final ApplicationContext applicationContext;
    private final Map<Long, ScheduledFuture<?>> activeJobs = Collections.synchronizedMap( new HashMap<>() );

    @EventListener( ContextRefreshedEvent.class )
    public void refresh()
    {
        log.info( "{} -> refresh", getClass().getSimpleName() );
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize( 10 );
        threadPoolTaskScheduler.setThreadNamePrefix( "Cron-Runner-" );
        threadPoolTaskScheduler.initialize();

        batchTaskService.findAll().stream().filter( b -> Boolean.TRUE.equals( b.active() ) ).forEach( batchTask -> startTask( batchTask,  threadPoolTaskScheduler ) );
    }

    public void startTask( BatchTaskResponse batchTaskResponse, ThreadPoolTaskScheduler threadPoolTaskScheduler )
    {
        log.info( "{} -> startTask", getClass().getSimpleName() );
        stopTask( batchTaskResponse );
        Job job = findJobByName( batchTaskResponse.taskName() );

        Runnable jobRunnable = () -> {
            try
            {
                jobOperator.start( job, new JobParametersBuilder().addString( "time", String.valueOf( System.currentTimeMillis() ) ).addString( "date", DateTimeFormatter.ofPattern( "yyyy-MM-dd" ).format( LocalDate.now( clock ) ) ).toJobParameters() );
            }
            catch ( JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException | InvalidJobParametersException | JobRestartException e )
            {
                log.error( "{} -> startTask", getClass().getSimpleName(), e );
            }

        };
        Runnable lockRunnable = () -> new DefaultLockingTaskExecutor( lockProvider ).executeWithLock( jobRunnable, new LockConfiguration( Instant.now( clock ), batchTaskResponse.taskName(), Duration.parse( "PT10M" ), Duration.parse( "PT10M" ) ) );
        activeJobs.put( batchTaskResponse.taskId(), threadPoolTaskScheduler.schedule( lockRunnable, new CronTrigger( batchTaskResponse.cron() ) ) );
    }

    public void stopTask( BatchTaskResponse batchTaskResponse )
    {
        log.info( "{} -> stopTask", getClass().getSimpleName() );
        ScheduledFuture<?> scheduledFuture = activeJobs.get( batchTaskResponse.taskId() );
        if ( scheduledFuture != null )
        {
            scheduledFuture.cancel( true );
            activeJobs.remove(  batchTaskResponse.taskId() );
        }
    }

    private Job findJobByName( String jobName )
    {
        Job job = (Job) applicationContext.getBean( jobName );
        if ( ObjectUtils.isEmpty( job ) ) throw new ApplicationException( String.format( "No pre-configured job found with name: %s", jobName ) );
        return job;
    }
}
