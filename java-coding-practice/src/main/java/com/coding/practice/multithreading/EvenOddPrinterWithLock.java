package com.coding.practice.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EvenOddPrinterWithLock {
  private int counter = 1;
  private final int MAX = 10;
  private final Lock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();

  public void printOdd() {
    while (counter <= MAX) {
      lock.lock();
      try {
        while (counter % 2 == 0) {
          condition.await();
        }
        if (counter <= MAX) {
          System.out.println("Odd  : " + counter);
          counter++;
          condition.signal();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }
  }

  public void printEven() {
    while (counter <= MAX) {
      lock.lock();
      try {
        while (counter % 2 != 0) {
          condition.await();
        }
        if (counter <= MAX) {
          System.out.println("Even : " + counter);
          counter++;
          condition.signal();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }
  }

  public static void main(String[] args) {
    EvenOddPrinterWithLock printer = new EvenOddPrinterWithLock();

    Thread oddThread = new Thread(() -> printer.printOdd());
    Thread evenThread = new Thread(() -> printer.printEven());

    oddThread.start();
    evenThread.start();
  }
}
