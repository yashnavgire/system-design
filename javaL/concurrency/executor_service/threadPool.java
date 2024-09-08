package javaL.concurrency.executor_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class threadPool {
    public static void main(String[] args) {
        System.out.println("thread pool");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        Runnable runnable = () -> { 
            try {
                System.out.println("runnable started thread: " + Thread.currentThread().getId()); 
                Thread.sleep(1000);
                System.out.println("runnable done thread: " + Thread.currentThread().getId());
            } catch(InterruptedException io) {} 
        };

        for (int i=0;i<5;i++) {
            executor.execute(runnable); // doesn't returns anything so cannot track the task status
        }

        for (int i=0;i<5;i++) {
            Future<?> future = executor.submit(runnable); //submit returns future
            try { future.get(); } catch(Exception e) {}   // get blocks until the thread associated with future is completed 
        }

        System.out.println("shutting down executor service");
        executor.shutdown();
    }
}
