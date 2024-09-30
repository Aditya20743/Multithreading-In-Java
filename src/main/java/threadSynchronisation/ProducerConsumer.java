package threadSynchronisation;

import java.util.List;
import java.util.ArrayList;

public class ProducerConsumer {
    public static void main(String[] args) {
        Worker worker = new Worker(5,0);

        Thread producer = new Thread(()->{
            try {
                worker.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(()->{
            try {
                worker.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producer.start();
        consumer.start();
    }
}

class Worker{
    private int sequence = 0;
    private final Integer maximum;
    private final Integer minimum;
    private final List<Integer> container;
    private final Object lock = new Object();

    public Worker(Integer param1, Integer param2){
        this.maximum = param1;
        this.minimum = param2;
        this.container = new ArrayList<>();
    }

    public void produce() throws InterruptedException{
        synchronized (lock){
            while(true){
                System.out.println(sequence + " Added to the container");
                container.add(sequence++);
                lock.notify();
                Thread.sleep(500);

                if(container.size() == maximum){
                    System.out.println("Container full, waiting for items to be removed...");
                    lock.wait();
                    Thread.sleep(500);
                }
            }
        }
    }

    public void consume() throws InterruptedException{
        synchronized (lock) {
            while (true) {

                System.out.println(container.remove(0) + " removed from the container");
                lock.notify();
                Thread.sleep(500);

                if(container.size() == minimum) {
                    System.out.println("container is empty, waiting for items to be added...");
                    lock.wait();
                    Thread.sleep(500);
                }
            }
        }
    }

}
