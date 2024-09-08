package javaL.concurrency.executor_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class singleThread {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> { 
            try {
                System.out.println("runnable started thread: " + Thread.currentThread().getId()); 
                Thread.sleep(1000);
                System.out.println("runnable done thread: " + Thread.currentThread().getId());
            } catch(InterruptedException io) {} 
        };

        executorService.submit(runnable);
        executorService.submit(runnable);

        try { Thread.sleep(100); } catch(Exception e) {};
        System.out.println("2 task submitted");

        executorService.shutdown();
    }
}
