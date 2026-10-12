package com.jos.ant.batch.writer;

import com.jos.ant.common.model.BirthdayNotification;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class BirthdayItemWriter implements ItemWriter<BirthdayNotification>
{
    public void write( Chunk<? extends BirthdayNotification> chunk )
    {
        log.info( "{} -> write", getClass().getSimpleName() );
        for ( BirthdayNotification notification: chunk )
        {
            log.info( "[ENVÍO DE CORREO] Destinatario: {} <{}> | Mensaje: {}", notification.name(), notification.email(), notification.message() );
            // TODO: JavaMailSender
        }
    }
}
