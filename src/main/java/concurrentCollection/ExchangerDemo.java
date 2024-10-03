package concurrentCollection;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        Thread one = new Thread(new FirstThread(exchanger));
        Thread two = new Thread(new SecondThread(exchanger));

        one.start();
        two.start();
    }
}

class FirstThread implements Runnable{
    private final Exchanger<Integer> exchanger;

    public FirstThread(Exchanger<Integer> exchanger){
        this.exchanger = exchanger;
    }
    @Override
    public void run(){
        try {
            int dataToSend = 21;
            System.out.println("First Thread is sending data: " + dataToSend);
            int receivedData = exchanger.exchange(dataToSend);
            System.out.println("First Thread received: "+ receivedData);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class SecondThread implements Runnable{
    private final Exchanger<Integer> exchanger;

    public SecondThread(Exchanger<Integer> exchanger){
        this.exchanger = exchanger;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(5000);
            int dataToSend = 69;
            System.out.println("Second thread is sending data: " + dataToSend);

            // Send data to the other thread and receive data in return
            int receivedData = exchanger.exchange(dataToSend);

            System.out.println("Second thread received: " + receivedData);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
