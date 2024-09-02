package threadSynchronisation;

public class SynchronisationDemo {
    private static int counter = 0;
    private synchronized static void increment(){
        counter++;
    }
    public static void main(String[] args) {
        Thread one = new Thread(()-> {
            for(int i=0;i<10000;i++)
                increment();
        });
    
        Thread two = new Thread(()-> {
            for(int i=0;i<10000;i++)
                increment();
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
        
        System.out.println("Counter value: "+ counter);
    }

}
