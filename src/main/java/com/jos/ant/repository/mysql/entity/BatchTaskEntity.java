package com.jos.ant.repository.mysql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table( name = "BATCH_TASK" )
public class BatchTaskEntity
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "SK_TASK_ID", nullable = false )
    private Long taskId;

    @Column( name = "TASK_NAME", nullable = false, length = 100 )
    private String taskName;

    @Column( name = "CRON", nullable = false, length = 20 )
    private String cron;

    @Column( name = "ACTIVE", nullable = false )
    private Boolean active;
}
