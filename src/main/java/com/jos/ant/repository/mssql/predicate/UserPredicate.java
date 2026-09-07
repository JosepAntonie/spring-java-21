package com.jos.ant.repository.mssql.predicate;

import com.jos.ant.common.payload.request.UserRequest;
import com.jos.ant.repository.mssql.entity.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserPredicate
{
    private UserPredicate()
    {
    }

    public static BooleanBuilder findAllByFilter( UserRequest user, String search )
    {
        log.info( "Predicate -> findAllByFilter" );
        BooleanBuilder builder = new BooleanBuilder();
        QUserEntity userEntity = QUserEntity.userEntity;
        if ( isNotNullOrEmpty( user.username() ) ) builder.and( userEntity.username.eq( user.username() ) );
        if ( user.active() != null ) builder.and( userEntity.active.eq( user.active() ) );
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