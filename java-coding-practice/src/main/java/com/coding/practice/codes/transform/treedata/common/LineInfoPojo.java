package com.coding.practice.codes.transform.treedata.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LineInfoPojo {
    private String name;
    private int indentationLevel;
    private int diffWithPrevious=0;
    private int relativeLevel;
    private int level;
    private String uniqueId;
    private String parentId="";
    private String parentName="";
    @Builder.Default
    List<LineInfoPojo> children=new ArrayList<>();
}
