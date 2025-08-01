package com.coding.practice.multithreading;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerSum {
  public static void main(String[] args) {
    SharedQueuePCS queue = new SharedQueuePCS(100);
    Thread producer = new Thread(new ProducerPCS(queue), "Producer");
    Thread consumer = new Thread(new ConsumerPCS(queue), "Consumer");

    producer.start();
    consumer.start();
  }
}

class SharedQueuePCS {
  private Queue<Integer> queue = new LinkedList<>();
  private final int capacity;
  private boolean finished = false;

  public SharedQueuePCS(int capacity) {
    this.capacity = capacity;
  }

  public synchronized void produce(int item) throws InterruptedException {
    while (queue.size() >= capacity) {
      wait();
    }
    queue.add(item);
    notifyAll();
  }

  public synchronized Integer consume() throws InterruptedException {
    while (queue.isEmpty()) {
      if (finished) return null; // No more items will come
      wait();
    }
    int item = queue.poll();
    notifyAll();
    return item;
  }

  public synchronized void setFinished() {
    finished = true;
    notifyAll();
  }

  public synchronized boolean isFinished() {
    return finished && queue.isEmpty();
  }
}

class ProducerPCS implements Runnable {
  private final SharedQueuePCS queue;

  public ProducerPCS(SharedQueuePCS queue) {
    this.queue = queue;
  }

  public void run() {
    try {
      for (int i = 1; i <= 10000; i++) {
        queue.produce(i);
      }
      queue.setFinished();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class ConsumerPCS implements Runnable {
  private final SharedQueuePCS queue;
  private long sum = 0;

  public ConsumerPCS(SharedQueuePCS queue) {
    this.queue = queue;
  }

  public void run() {
    try {
      while (true) {
        Integer item = queue.consume();
        if (item == null) break; // All done
        int result = item * 2;
        sum += result;
//        Thread.sleep(2);
      }
      System.out.println("Final Sum: " + sum);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
