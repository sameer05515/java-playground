package com.coding.practice.anonymousAndSam;

public class Test10 {
  public static void main(String[] args) {
    AiseHi aiseHi = new AiseHi() {
          //      @Override
          //      public void printMe() {
          //        AiseHi.super.printMe();
          //      }
        };

    aiseHi.printMe();

    SAM sam = (a, b) -> a + b;
    System.out.println(sam.perform(2, 6));
  }
}

interface AiseHi {
  default void printMe() {
    System.out.println("Aise Hi");
  }
}

interface SAM {
  int perform(int a, int b);

  default void printMe() {
    System.out.println("Aise Hi");
  }
}
