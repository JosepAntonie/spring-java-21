package com.jos.ant.batch.processor;

import com.jos.ant.common.model.BirthdayNotification;
import com.jos.ant.repository.mssql.entity.PersonEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class BirthdayItemProcessor implements ItemProcessor<PersonEntity, BirthdayNotification>
{
    public BirthdayNotification process( PersonEntity personEntity )
    {
        log.info( "{} -> process", getClass().getSimpleName() );
        String message = "¡Feliz cumpleaños, " + personEntity.getName() + "! Te deseamos lo mejor en tu día.";
        String fullName = String.format( "%s %s %s", personEntity.getName(), personEntity.getLastName(), personEntity.getLastNameMother() );
        return new BirthdayNotification( personEntity.getMail(), fullName, message );
    }
}
