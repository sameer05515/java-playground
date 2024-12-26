package com.coding.practice.multithreading;

// The volatile keyword in Java ensures visibility of changes to variables across threads.

public class VolatileExample {
  private static volatile boolean flag = false;

  public static void main(String[] args) {
    Thread writer =
        new Thread(
            () -> {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
              }
              flag = true;
              System.out.println("Flag set to true");
            });

    Thread reader =
        new Thread(
            () -> {
              while (!flag) {
                // busy wait
              }
              System.out.println("Reader thread detected flag change");
            });

    writer.start();
    reader.start();
  }
}
