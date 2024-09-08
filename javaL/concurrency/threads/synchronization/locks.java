package javaL.concurrency.threads.synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class locks {
    public static void main(String[] args) {
        //reentrant - A thread holding a lock on object in one critical section will also get a lock on other critical section
        Lock lock = new ReentrantLock();
        lock.lock();    //mutex
        //critical section
        lock.unlock();

        //readwrite - used for frequent reads / less writes
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Runnable readRunnable = () -> { 
            readWriteLock.readLock().lock();
            //critical section
            System.out.println("read lock acquired, thread: " + Thread.currentThread().getId());
            try { Thread.sleep(4000); } catch(Exception e) {}

            readWriteLock.readLock().unlock();
            System.out.println("read lock released, thread: " + Thread.currentThread().getId());
        };

        Runnable writeRunnable = () -> { 
            readWriteLock.writeLock().lock();
            //critical section
            System.out.println("write lock acquired, thread: " + Thread.currentThread().getId());
            try { Thread.sleep(2000); } catch(Exception e) {}

            readWriteLock.writeLock().unlock();
            System.out.println("write lock released, thread: " + Thread.currentThread().getId());
        };


        Thread rth1 = new Thread(readRunnable);
        Thread rth2 = new Thread(readRunnable);

        Thread wth1 = new Thread(writeRunnable);

        rth1.start();
        rth2.start();
        wth1.start();
    }
}
