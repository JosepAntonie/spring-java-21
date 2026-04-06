package com.jos.ant.common.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public abstract class AuditingPayload<U>
{
    @Schema( accessMode = Schema.AccessMode.READ_ONLY )
    @JsonProperty( access = JsonProperty.Access.READ_ONLY )
    private U createdBy;

    @Schema( accessMode = Schema.AccessMode.READ_ONLY )
    @JsonProperty( access = JsonProperty.Access.READ_ONLY )
    private Date createdDate;

    @Schema( accessMode = Schema.AccessMode.READ_ONLY )
    @JsonProperty( access = JsonProperty.Access.READ_ONLY )
    private U lastModifiedBy;

    @Schema( accessMode = Schema.AccessMode.READ_ONLY )
    @JsonProperty( access = JsonProperty.Access.READ_ONLY )
    private Date lastModifiedDate;
}
