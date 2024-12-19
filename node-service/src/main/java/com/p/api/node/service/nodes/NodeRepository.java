package com.p.api.node.service.nodes;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface NodeRepository extends MongoRepository<Node, String> {
  Optional<Node> findByUniqueId(String uniqueId);

  @Query(value = "{}", fields = "{ 'uniqueId' : 1 }")
  List<Node> findAllUniqueIds();
}
