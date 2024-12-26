package com.coding.practice.multithreading;

import java.util.*;
import java.util.concurrent.locks.*;

public class WaitNotifyDemoWithReentrantLock {
  private static final int MAX_CAPACITY = 5;
  private final List<String> buffer = new LinkedList<>();

  private final ReentrantLock lock = new ReentrantLock();
  private final Condition notFull = lock.newCondition();
  private final Condition notEmpty = lock.newCondition();

  public static void main(String[] args) {
    WaitNotifyDemoWithReentrantLock demo = new WaitNotifyDemoWithReentrantLock();

    Thread producer = new Thread(demo.new Producer(), "Producer");
    Thread consumer = new Thread(demo.new Consumer(), "Consumer");

    producer.start();
    consumer.start();
  }

  class Producer implements Runnable {
    int item = 1;

    public void run() {
      while (true) {
        lock.lock();
        try {
          while (buffer.size() == MAX_CAPACITY) {
            System.out.println("Buffer full. Producer waiting...");
            notFull.await();
          }

          String value = "Item-" + (item++);
          buffer.add(value);
          System.out.println("Produced: " + value);
          notEmpty.signal(); // Signal consumer

        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        } finally {
          lock.unlock();
        }

        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  class Consumer implements Runnable {
    public void run() {
      while (true) {
        lock.lock();
        try {
          while (buffer.isEmpty()) {
            System.out.println("Buffer empty. Consumer waiting...");
            notEmpty.await();
          }

          String item = buffer.remove(0);
          System.out.println("Consumed: " + item);
          notFull.signal(); // Signal producer

        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        } finally {
          lock.unlock();
        }

        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}
