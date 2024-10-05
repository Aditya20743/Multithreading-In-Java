package otherConcepts;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    // Semaphore with 3 permits
    private static final Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Task(i)).start();
        }
    }

    static class Task implements Runnable {
        private int id;

        public Task(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println("Thread " + id + " is trying to acquire a permit...");
                semaphore.acquire(); // Acquire a permit
                System.out.println("Thread " + id + " acquired a permit!");

                // Simulate work by sleeping
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("Thread " + id + " is releasing the permit.");
                semaphore.release(); // Release the permit
            }
        }
    }
}
