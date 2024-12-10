package com.coding.practice.engineering.digest.collection.tutorials;

import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);

    Object[] array = list.toArray();
  }
}

abstract class AnonymousNode {
  //    protected String uniqueId;
  public abstract String getUniqueId();
}

abstract class Node extends AnonymousNode {
  public abstract String getUniqueId();

  public abstract String getName();
}

class SppNode extends Node {
  protected String uniqueId;
  protected String name;

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  @Override
  public String getName() {
    return name;
  }
}
