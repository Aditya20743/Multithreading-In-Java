package executorService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadExecutorDemo {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        long startTime = System.currentTimeMillis();

        service.scheduleAtFixedRate(new ProbeTask(startTime), 1000,2000, TimeUnit.MILLISECONDS);

        try{
            if(!service.awaitTermination(10000, TimeUnit.MILLISECONDS)){
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ProbeTask implements Runnable{
    private final long startTime;

    public ProbeTask(long startTime){
        this.startTime = startTime;
    }
    public void run(){
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        System.out.println("Probing end point for updates... Instance- " + elapsedTime + " milliseconds");
    }
}