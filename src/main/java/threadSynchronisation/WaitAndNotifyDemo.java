package threadSynchronisation;

public class WaitAndNotifyDemo {

    private static Object lock = new Object();
    public static void main(String[] args) {
        Thread one = new Thread(()->{
            try {
                one();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            };
        });

        Thread two = new Thread(()->{
            try {
                two();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            };
        });

        one.start();
        two.start();
    }

    private static void one() throws InterruptedException{
        synchronized (lock) {
            System.out.println("Thread One: Hello from Thread One!");
            lock.wait();
            System.out.println("Thread One: Woken up");
        }
    }

    private static void two() throws InterruptedException{
        synchronized (lock) {
            System.out.println("Thread Two: Hello from Thread Two!");
            lock.notify();
            System.out.println("Thread Two: Notified");
        }
    }
}
