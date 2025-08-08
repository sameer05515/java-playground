package com.prem.vocab.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Example {
    private int id;
    private String text;
}