package basicMultithreading;

public class DaemonUserThreadDemo {
    public static void main(String[] args) {
        Thread bgThread = new Thread(new DaemonHelper());
        Thread userThread = new Thread(new UserThreadHelper());
        bgThread.setDaemon(true);

        bgThread.start();
        userThread.start();

        // bgThread is terminated after userThread is finished executing after 5 seconds.
    }
}

class DaemonHelper implements Runnable{
    @Override
    public void run() {
        int count =0;
        while(count< 500){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            System.out.println("Daemon helper is running...");
        }
    }
}

class UserThreadHelper implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("User Thread is done with execution ");
    }
}