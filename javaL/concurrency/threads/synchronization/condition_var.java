package javaL.concurrency.threads.synchronization;

/*
 * https://jenkov.com/tutorials/java-concurrency/thread-signaling.html

   When to Use Thread Signaling Over Locks:

    Coordination Between Threads:
        Scenario: You need one thread to wait for a specific condition to be met by another thread before proceeding.
        Example: In a producer-consumer scenario, a consumer thread might need to wait for the producer to produce an item before it can consume it. Here, thread signaling (using a condition variable) is appropriate because it allows the consumer to wait efficiently until it's notified that the item is available.

    Avoiding Busy-Waiting:
        Scenario: A thread needs to wait for a condition to become true but you want to avoid using CPU resources while waiting.
        Example: Instead of using a loop that continuously checks if a condition is true (which can waste CPU cycles), you can use thread signaling. The thread can wait on a condition variable, which puts it to sleep until another thread signals that the condition is met.

    Implementing Event-Driven Logic:
        Scenario: Your program logic depends on certain events, and threads need to wait for these events to occur.
        Example: If a thread needs to perform an action only after another thread finishes a task, you can use signaling to notify the waiting thread that it can proceed.

    Handling Complex Dependencies:
        Scenario: Multiple threads need to be synchronized based on more complex conditions, like waiting for multiple events to occur.
        Example: Suppose you have multiple threads performing different tasks, and a final thread that should only proceed after all tasks are completed. Here, signaling can be used to notify the final thread when all other threads have finished their work.

When to Use Locks:

    Mutual Exclusion:
        Scenario: You need to protect a shared resource from concurrent access to prevent race conditions.
        Example: If multiple threads are accessing and modifying a shared data structure, a lock ensures that only one thread can modify the structure at a time, preventing inconsistent states.

    Simple Resource Access:
        Scenario: The main goal is to prevent multiple threads from accessing a critical section simultaneously.
        Example: When threads are reading from and writing to a shared file, using a lock ensures that one thread can complete its operation before another thread starts.
 */


class MonitorObject {}

class MyWaitNotify{

    MonitorObject myMonitorObject = new MonitorObject();
  
    public void doWait(){
      synchronized(myMonitorObject){
        try{
          System.out.println("waiting thread: " + Thread.currentThread().getId());
          myMonitorObject.wait();
          System.out.println("active thread: " + Thread.currentThread().getId());
        } catch(InterruptedException e){}
      }
    }
  
    public void doNotify(){
      synchronized(myMonitorObject){
        System.out.println("notify thread: " + Thread.currentThread().getId());
        myMonitorObject.notify();
      }
    }

    public void doNotifyAll(){
      synchronized(myMonitorObject){
        System.out.println("notify all thread: " + Thread.currentThread().getId());
        myMonitorObject.notifyAll();
      }
    }
  }

public class condition_var {

    public static void main(String[] args) {

        MyWaitNotify service = new MyWaitNotify();

        Runnable runnableWait = () -> {
          service.doWait();
        };

        Runnable runnableNotify = () -> {
          service.doNotify();
        };

        Runnable runnableNotifyAll = () -> {
          service.doNotifyAll();
        };

        Thread th1 = new Thread(runnableWait);
        Thread th2 = new Thread(runnableWait);
        th1.start();
        th2.start();

        // Thread th3 = new Thread(runnableNotify);
        Thread th4 = new Thread(runnableNotifyAll);
        th4.start();
        Thread th5 = new Thread(runnableNotifyAll);
        th5.start();
    }
}
