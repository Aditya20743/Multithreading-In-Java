package otherConcepts;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockDemo {
    private final ReentrantLock lockA = new ReentrantLock(true);
    private final ReentrantLock lockB = new ReentrantLock(true);

    public void workerOne(){
        lockA.lock();
        System.out.println("Worker One has acquired the LockA");
        try{
            Thread.sleep(200);
            lockB.lock();
            System.out.println("Worker One has acquired the LockB");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lockA.unlock();
            lockB.unlock();
        }
    }

    public void workerTwo(){
        lockB.lock();
        System.out.println("Worker Two has acquired the LockB");
        try{
            Thread.sleep(200);
            lockA.lock();
            System.out.println("Worker Two has acquired the LockA");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lockA.unlock();
            lockB.unlock();
        }
    }

    public static void main(String[] args) {
        DeadlockDemo deadlock = new DeadlockDemo();
        new Thread(deadlock::workerOne, "Worker One").start();
        new Thread(deadlock::workerTwo, "Worker Two").start();

        new Thread(() -> {
            ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
            while (true) {
                long[] threadIds = mxBean.findDeadlockedThreads();
                if (threadIds != null) {
                    ThreadInfo[] threadInfo = mxBean.getThreadInfo(threadIds);
                    System.out.println("Dead Lock detected!");
                    for (long threadId : threadIds) {
                        ThreadInfo threadInfos = mxBean.getThreadInfo(threadId);
                        System.out.println("Thread with ID " + threadId + " is in Dead Lock");
//                        System.out.println(threadInfos);
                    }
                    break;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
