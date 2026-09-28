package com.prem.vocab.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meaning {
    private int id;
    private String text;
}