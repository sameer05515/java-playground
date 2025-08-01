package com.coding.practice.multithreading;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerExample {
  public static void main(String[] args) {
    SharedQueue queue = new SharedQueue(5);

    Thread producer = new Thread(new Producer(queue), "Producer");
    Thread consumer = new Thread(new Consumer(queue), "Consumer");

    producer.start();
    consumer.start();
  }
}

class SharedQueue {
  private Queue<Integer> queue = new LinkedList<>();
  private int capacity;

  public SharedQueue(int capacity) {
    this.capacity = capacity;
  }

  public synchronized void produce(int item) throws InterruptedException {
    while (queue.size() == capacity) {
      wait(); // wait until consumer consumes
    }
    queue.add(item);
    System.out.println(Thread.currentThread().getName() + " produced " + item);
    notify(); // notify consumer
  }

  public synchronized int consume() throws InterruptedException {
    while (queue.isEmpty()) {
      wait(); // wait until producer produces
    }
    int item = queue.poll();
    System.out.println(Thread.currentThread().getName() + " consumed " + item);
    notify(); // notify producer
    return item;
  }
}

class Producer implements Runnable {
  private SharedQueue queue;

  public Producer(SharedQueue queue) {
    this.queue = queue;
  }

  public void run() {
    int value = 0;
    while (true) {
      try {
        queue.produce(value++);
        Thread.sleep(500); // slow down for readability
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class Consumer implements Runnable {
  private SharedQueue queue;

  public Consumer(SharedQueue queue) {
    this.queue = queue;
  }

  public void run() {
    while (true) {
      try {
        queue.consume();
        Thread.sleep(1000); // slow down for readability
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
