package javaL.concurrency.threads.synchronization;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * synchronisation is required for 'shared variables state change' which are 'not atomic' across the threads (a.k.a critical section)
 * 
 * more detailed info: https://jenkov.com/tutorials/java-concurrency/synchronized.html
 */

class CommonThread extends Thread {

    public CommonThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        // following is not a critical section as local variables are used which are created and accessed separately in each thread stack
        int count = 0;
        while (count!=10) {
            System.out.println("Thread name: " + getName() + " count: " + count);
            count++;
        } 
    }        
}

class CommonThreadWithCriticalSection extends Thread {
    
    public CommonThreadWithCriticalSection(String name) {
        super(name);
    }

    @Override
    public void run() {
        //critical section as count and nCnt are shared variables
        while (synchronize.count.get()<=100 || synchronize.nCnt<=100) {
            synchronize.nCnt++;
            System.out.println("Thread name: " + getName() + " atomic count: " + synchronize.count.incrementAndGet() + " normal count: " + synchronize.nCnt);
        } 
    }
}

class CommonThreadWithCriticalSectionSynchronizeBlock extends Thread {
    
    public CommonThreadWithCriticalSectionSynchronizeBlock(String name) {
        super(name);
    }

    @Override
    public void run() {
        //critical section as count and nCnt are shared variables
        synchronized(synchronize.synchObj) {
            while (synchronize.count.get()<=100 || synchronize.nCnt<=100) {
                synchronize.nCnt++;
                System.out.println("Thread name: " + getName() + " atomic count: " + synchronize.count.incrementAndGet() + " normal count: " + synchronize.nCnt);
            }
        }
    }
}

public class synchronize {

    static AtomicInteger count = new AtomicInteger(0);
    static int nCnt = 0;
    static Object synchObj = new Object();

    public static void main(String[] args) {
        // System.out.println("running multiple threads with common task(function) without critical section");
        // for(int i=0; i<5; i++) {
        //     Thread th = new CommonThread("" + i);
        //     th.start();
        // }

        System.out.println("running multiple threads with common task(function) with critical section");
        for(int i=0; i<5; i++) {
            Thread th = new CommonThreadWithCriticalSectionSynchronizeBlock("" + i);
            th.start();
        }
    }
}
