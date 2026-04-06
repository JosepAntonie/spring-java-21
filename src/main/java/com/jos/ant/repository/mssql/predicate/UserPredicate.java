package com.jos.ant.repository.mssql.predicate;

import com.jos.ant.common.payload.UserPayload;
import com.jos.ant.repository.mssql.entity.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserPredicate
{
    private UserPredicate()
    {
    }

    public static BooleanBuilder findAllByFilter( UserPayload user, String search )
    {
        log.info( "Predicate -> findAllByFilter" );
        BooleanBuilder builder = new BooleanBuilder();
        QUserEntity userEntity = QUserEntity.userEntity;
        if ( isNotNullOrEmpty( user.getUsername() ) )
        {
            builder.and( userEntity.username.eq( user.getUsername() ) );
        }
        if ( user.getActive() != null )
        {
            builder.and( userEntity.active.eq( user.getActive() ) );
        }
        if ( isNotNullOrEmpty( search ) )
        {
            builder.and( userEntity.username.stringValue().toLowerCase().like( "%" + search + "%" ) );
        }
        return builder;
    }

    private static boolean isNotNullOrEmpty( String text )
    {
        return text != null && !text.isEmpty();
    }
}