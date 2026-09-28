package com.prem.vocab.model;

import lombok.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordEntry {
    private int id;
    private String word;
    private String type;
    @Builder.Default
    private List<Meaning> meanings = new ArrayList<>();
    @Builder.Default
    private List<Example> examples = new ArrayList<>();
}