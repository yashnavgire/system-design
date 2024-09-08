package javaL.futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CustomCompletableFuture {
    public CompletableFuture<Boolean> handleDbWrite(Object request, ExecutorService executorService) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        //process I/O request asynchronously
        CompletableFuture.runAsync(() -> {
            System.out.println("processing I/O request in thread: " + Thread.currentThread().getId() + " request: " + request);
            
            try { Thread.sleep(1000); } catch(Exception e) {}
            
            System.out.println("done I/O task in thread: " + Thread.currentThread().getId() + " request: " + request);
            future.complete(true);
        }, executorService);

        return future;
    }
}        

class DirectCompletableFuture {
    public void performAsyncTask(ExecutorService executor) {
        Runnable runnable = () -> { 
            try { 
                System.out.println("async task started in thread: " + Thread.currentThread().getId()); 
                Thread.sleep(1000); 
                System.out.println("async task done"); 
            } catch(Exception e) {
                System.out.println("");
            } 
        };

        CompletableFuture.runAsync(runnable, executor).thenAccept((completableFutureObj) -> { 
            System.out.println("async task done ForkJoinPool direct callback in thread: " + Thread.currentThread().getId()); 
        });
    }
}

/*
 * resource: https://www.youtube.com/watch?v=ImtZgX1nmr8
 * 
 * 
 * CompleteFuture unlike normal Future avoids blocking of main thread to perform an action once CompletableFuture has completed its task, by providing the 
 * methods such as: thenAccept, thenApply (chaining similar to promises in js)
 *  
 * thenAccept/thenApply runnable runs in same thread where the parent task was executed.
 * 
 * thenAcceptSync/thenApplyAsync runnable runs in same/different thread from thread pool
 * 
 */
public class completable_futures {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);        

        CustomCompletableFuture cFutureExample = new CustomCompletableFuture();
        DirectCompletableFuture dFutureExample = new DirectCompletableFuture();

        System.out.println("trigerring direct completable future in main thread: " + Thread.currentThread().getId());
        dFutureExample.performAsyncTask(executor);

        System.out.println("trigerring custom completable future in main thread: " + Thread.currentThread().getId());
        cFutureExample.handleDbWrite("thenAccept", executor).thenAccept((cFuture) -> {
            System.out.println("completed the future for I/O, running thenAccept runnable in thread: " + Thread.currentThread().getId());
        });

        
        System.out.println("trigerring custom completable future with thenAcceptAsync in main thread: " + Thread.currentThread().getId());
        cFutureExample.handleDbWrite("thenAcceptAsync", executor).thenAcceptAsync((cFuture) -> {
            System.out.println("completed the future for I/O, running thenAcceptAsync runnable in thread: " + Thread.currentThread().getId());
        });

        // executor.shutdown();
    }
}
