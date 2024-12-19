package com.p.api.node.service.nodes;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "nodes")
public class Node {
  private String uniqueId;
  private String type;
  private String name;
  @Builder.Default private Map<String, String> metadata = new HashMap<>();
}
