package com.jos.ant.common.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilterPayload<U>
{
    @NotNull @Min( 0 )
    private Integer pageNumber;

    @NotNull @Min( 1 )
    private Integer pageSize;

    private String search;

    private U payload;
}
