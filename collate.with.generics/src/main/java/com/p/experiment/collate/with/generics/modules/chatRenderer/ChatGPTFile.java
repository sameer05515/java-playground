package com.p.experiment.collate.with.generics.modules.chatRenderer;

import com.p.experiment.collate.with.generics.modules.common.PojoOfEP;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatGPTFile extends PojoOfEP {
    private String heading;
    private String location;
    private boolean isLatest;
}
