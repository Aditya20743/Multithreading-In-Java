package executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorDemo {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            for (int i = 0; i < 5; i++) {
                service.execute(new Task(i));
            }
        } finally {
            service.shutdown();  // Ensure the ExecutorService is properly shut down
        }
    }
}

class Task implements Runnable{
    private final int taskid;

    public Task(int taskid){
        this.taskid = taskid;
    }

    @Override
    public void run(){
        System.out.println("Task with ID "+ taskid + " being executed by Thread " + Thread.currentThread().getName());
        try{
            Thread.sleep(500);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
