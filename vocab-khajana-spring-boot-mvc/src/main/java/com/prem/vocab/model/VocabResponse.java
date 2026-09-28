package com.prem.vocab.model;

import lombok.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VocabResponse {
    private int txtOffset;
    private int txtCount;
    private List<WordEntry> words;
}