package com.p.ex.multithreading.k_reentrant_locks;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Source:<em>
 * http://www.journaldev.com/2377/java-lock-example-and-concurrency-lock-vs-synchronized</em>
 * <p>
 * {@link Lock}:
 * This is the base interface for Lock API. It provides all the features
 * of synchronized keyword with additional ways to create different Conditions
 * for locking, providing timeout for thread to wait for lock. Some of the
 * important methods are {@link Lock#lock()} to
 * acquire the lock, {@link Lock#unlock()} to release
 * the lock, {@link Lock#tryLock()} to wait for lock
 * for a certain period of time,
 * {@link Lock#newCondition()}
 * to create the Condition etc.
 * <p>
 * {@link ReentrantLock}:
 * This is the most widely used implementation class of Lock
 * interface. This class implements the Lock interface in similar way as
 * synchronized keyword. (see App.java for more)
 * <p>
 * {@link Condition}:
 * Condition objects are similar to Object wait-notify model with
 * additional feature to create different sets of wait. A Condition object is
 * always created by Lock object. Some of the important methods are
 * {@link Condition#await()}
 * that is similar to {@link Object#wait()}.
 * {@link Condition#signal()} and
 * {@link Condition#signalAll()}
 * that are similar to
 * {@link Object#notify()} and {@link Object#notifyAll()} methods.
 * <br><br>
 * Codes with minor comments are from
 * <a href="http://www.caveofprogramming.com/youtube/">
 * <em>http://www.caveofprogramming.com/youtube/</em>
 * </a>
 * <br>
 * also freely available at
 * <a href="https://www.udemy.com/java-multithreading/?couponCode=FREE">
 *     <em>https://www.udemy.com/java-multithreading/?couponCode=FREE</em>
 * </a>
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
public class Runner {

    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        lock.lock();
        System.out.println("Waiting ....");
        cond.await();
        System.out.println("Woken up!");
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("Press the return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key!");
        cond.signal();
        try {
            increment();
        } finally {
            //should be written to unlock Thread whenever signal() is called
            lock.unlock();
        }
    }

    public void finished() {
        System.out.println("Count is: " + count);
    }
}
