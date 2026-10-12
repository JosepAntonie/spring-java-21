package com.jos.ant.repository.mysql.predicate;

import com.jos.ant.common.payload.request.BatchTaskRequest;
import com.jos.ant.repository.mysql.entity.QBatchTaskEntity;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

@Log4j2
public class BatchTaskPredicate
{
    private BatchTaskPredicate()
    {
    }

    public static BooleanBuilder findAllByFilter( BatchTaskRequest batchTaskRequest, String search )
    {
        log.info( "{} -> findAllByFilter", BatchTaskPredicate.class.getSimpleName() );
        BooleanBuilder builder = new BooleanBuilder();
        QBatchTaskEntity batchTaskEntity = QBatchTaskEntity.batchTaskEntity;
        if ( StringUtils.hasText( batchTaskRequest.taskName() ) ) builder.and( batchTaskEntity.taskName.eq( batchTaskRequest.taskName() ) );
        if ( StringUtils.hasText( batchTaskRequest.cron() ) ) builder.and( batchTaskEntity.cron.eq( batchTaskRequest.cron() ) );
        if ( batchTaskRequest.active() != null ) builder.and( batchTaskEntity.active.eq( batchTaskRequest.active() ) );
        if( StringUtils.hasText( search ) )
        {
            builder.and( batchTaskEntity.taskName.stringValue().like( "%" + search + "%" ) ).or( batchTaskEntity.cron.stringValue().like( "%" + search + "%" ) );
        }
        return builder;
    }
}
