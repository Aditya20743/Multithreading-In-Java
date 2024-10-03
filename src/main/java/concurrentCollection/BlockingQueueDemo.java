package concurrentCollection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {

    static final int QUEUE_CAPACITY = 10;
    static BlockingQueue<Integer> taskQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

    public static void main(String[] args) {
        // producer thread
        Thread producerThread = new Thread(()->{
            try {
                for (int i = 0; i <= 20; i++) {
                    taskQueue.put(i);
                    System.out.println("Task Produced: " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumerThreadOne = new Thread(()->{
            try {
                while(true){
                   int taskId= taskQueue.take();
                   processTask(taskId, "Consumer One");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumerThreadTwo = new Thread(()->{
            try {
                while(true){
                    int taskId= taskQueue.take();
                    processTask(taskId, "Consumer Two");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producerThread.start();
        consumerThreadOne.start();
        consumerThreadTwo.start();
    }

    private static void processTask(int taskId, String consumerName) throws InterruptedException {
        // processing the task
        System.out.println("Task being processed by " + consumerName + ": "+ taskId);
        Thread.sleep(5000);
        System.out.println("Task consumed by "+ consumerName+": " +taskId);
    }
}



