package javaL.concurrency.threads;

/*
 * creating and starting threads 
 * 
 * link: https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html
 */
public class creation {

    static Thread createThread(Runnable runnable) {
        return new Thread(runnable);    
    }

    public static void main(String[] args) {
        System.out.println("thread creation example"); 

        Thread thr = createThread(() -> { System.out.println("Thread with passing anonymous runnable to Thread constructor started \n "); });
    
        thr.start();
    }
}
