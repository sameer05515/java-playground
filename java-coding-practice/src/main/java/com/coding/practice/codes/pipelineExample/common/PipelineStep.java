package com.coding.practice.codes.pipelineExample.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PipelineStep<T,R>{
    private String stepName;
    private Function<T, Boolean> validator;
    private Function<T, R> processor;
    private String errorMessage;
}
