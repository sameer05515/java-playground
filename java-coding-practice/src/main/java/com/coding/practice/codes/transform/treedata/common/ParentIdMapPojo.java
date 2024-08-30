package com.coding.practice.codes.transform.treedata.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParentIdMapPojo {
    private String uniqueId;
    private String parentId="";
}
