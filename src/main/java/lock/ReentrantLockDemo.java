package lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int sharedData = 0;

    public void methodA(){
        lock.lock();
        try{
            // Critical Section -> shared data
            Thread.sleep(2000);
            sharedData++;
            System.out.println("Method A: sharedData = "+ sharedData);
            // call methodB(), which also requires the lock
            // hence reentrant behaviour is showcased by entering methodB
            methodB();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void methodB(){
        lock.lock();
        try{
            Thread.sleep(1000);
            sharedData--;
            System.out.println("Method B: sharedData = " + sharedData);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo example = new ReentrantLockDemo();

        // Create and start multiple threads
        for(int i=0;i<15;i++){
            new Thread(example::methodA).start();
        }
    }
}
