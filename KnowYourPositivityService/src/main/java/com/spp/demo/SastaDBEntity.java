package com.spp.demo;

public class SastaDBEntity {

  private String id;
  private String value;
  private SastaDBEntityType type;
  private String parentId;

  public SastaDBEntity(String id, String value, SastaDBEntityType type, String parentId) {
    this.id = id;
    this.value = value;
    this.type = type;
    this.parentId = parentId;
  }

  public String getId() {
    return id;
  }

  public String getValue() {
    return value;
  }

  public SastaDBEntityType getType() {
    return type;
  }

  public String getParentId() {
    return parentId;
  }
}
