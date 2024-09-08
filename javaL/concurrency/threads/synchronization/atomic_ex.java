package javaL.concurrency.threads.synchronization;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/*
 * guarantees visibility and atomicity
 * 
 */
public class atomic_ex {
    AtomicInteger atomicInteger;
    AtomicLong atomicLong;
    AtomicBoolean atomicBoolean;

    public atomic_ex() {
        this.atomicInteger = new AtomicInteger(0);
        this.atomicLong = new AtomicLong(0);
        this.atomicBoolean = new AtomicBoolean(false);
    }

    public static void main(String[] args) {        
        System.out.println("hello world!!");
        atomic_ex example = new atomic_ex();
        
        example.mutex_lock();
        // example.mutex_lock();
        example.mutex_unlock();
    }

    private void mutex_lock() {
        while(!atomicBoolean.compareAndSet(false, true)); //wait until unlocked
        System.out.println("mutex locked..");
    }

    private void mutex_unlock() {
        atomicBoolean.set(false);
        System.out.println("mutex unlocked..");
    }
}

/*
 * Compare And Swap and other atomic operation uses memory barriers and cache coherence mechanism to ensure visibility.
 * Therefore variables within the critical section protected by custom mutex using CAS atomic operation or library mutex 
 * need not be declared as volatile. operations on those variables are inherently atomic due to locks
 */
