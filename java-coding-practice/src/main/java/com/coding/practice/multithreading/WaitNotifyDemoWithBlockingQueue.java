package com.coding.practice.multithreading;

// import java.util.concurrent.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WaitNotifyDemoWithBlockingQueue {
  private static final int MAX_CAPACITY = 5;
  private final BlockingQueue<String> queue = new LinkedBlockingQueue<>(MAX_CAPACITY);

  public static void main(String[] args) {
    WaitNotifyDemoWithBlockingQueue demo = new WaitNotifyDemoWithBlockingQueue();

    Thread producer = new Thread(demo.new Producer(), "Producer");
    Thread consumer = new Thread(demo.new Consumer(), "Consumer");

    producer.start();
    consumer.start();
  }

  class Producer implements Runnable {
    int item = 1;

    public void run() {
      while (true) {
        try {
          String value = "Item-" + (item++);
          queue.put(value); // blocks if queue is full
          System.out.println("Produced: " + value);

          Thread.sleep(((item % 3) + 1) * 100); // simulate delay
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  class Consumer implements Runnable {
    public void run() {
      while (true) {
        try {
          String item = queue.take(); // blocks if queue is empty
          System.out.println("Consumed: " + item);

          Thread.sleep(600); // simulate processing
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}
