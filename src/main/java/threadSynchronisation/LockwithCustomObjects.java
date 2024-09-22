package threadSynchronisation;

// The goal of this program is to increment two counters (counter1 and counter2) using two different threads (Thread one and Thread two), while ensuring that the operations on each counter happen in a synchronized manner to avoid race conditions.
public class LockwithCustomObjects {

    private static int counter1 = 0; 
    private static int counter2 =0;

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread one = new Thread(()-> {
            for(int i=0;i<10000;i++){
                increment1();
            }
        });

        Thread two = new Thread(()-> {
            for(int i=0;i<10000;i++){
                increment2();
            }
        });

        one.start();
        two.start();
        
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(counter1 + " -- " + counter2);
    }

    private static void increment1(){
        synchronized(lock1){
            counter1++;
        }
    }

    private static void increment2(){
        synchronized(lock2){
            counter2++;
        }
    }
}
