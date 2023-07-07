

// Working with deadlock.
public class DeadLockLabPart1 {

    final Object resource1 = new Object();
    final Object resource2 = new Object();

    final int n = 1;

    Thread t1 = new Thread( new Runnable() {
        public void run() {
            // synchronized (resource1) {
                for (int x = 1; x <= n; x++) {
                }
                // synchronized (resource2) {
                    System.out.println();
                    for (int i = 1; i <= 10; i++) {
                        System.out.print(" t1: " + i);
                    }

                }
            // }
        // }
    });

    Thread t2 = new Thread( new Runnable() {
        public void run() {
            // synchronized (resource2) {
                for (int x = 1; x <= n; x++) {
                }
                // synchronized (resource1) {
                    System.out.println();
                    for (int i = 1; i <= 10; i++) {
                        System.out.print(" t2: " + i);

                    }
                }
            // }
        // }
    });

    public void foo() {
        // Start up two threads that may become deadlocked.
        t1.start();
        t2.start();
    }
    public static void main(String[] args) {
        System.out.println("About to startup and run two threads.");
        new DeadLockLabPart1().foo();
        System.out.println("Startup complete but threads may still be running.");
    }
}



// 1. What is the largest value of n (choosing n as a power of 10) that you can use that mostly stays out of deadlock?
//    Your answer only needs to be approximate.
//  n = 1000

// 2. What is the smallest value of n (choosing n as a power of 10) that you can use to reliably reach deadlock?
//    Your answer only needs to be approximate.
//  n = 1

// 3. How does this system behave if t1 and t2 access their shared resources in the exact same order? Make the necessary changes to the code and test it.
//    Explain why you see what you see. Does the system still reach deadlock? Why or why not?
//  The system will never reach deadlock, because two processes can access to a single resource at the same time.

// 4. Remove all of the synchronization from the code. Remove the "synchronized (resourceX)" coding
//    What do you see when you run the program?
// A different result every time, because two threads will run alternately and randomly.