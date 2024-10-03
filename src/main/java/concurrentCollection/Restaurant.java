package concurrentCollection;

import java.util.concurrent.CountDownLatch;

public class Restaurant {
    public static void main(String[] args) throws InterruptedException{
        int numberOfChefs = 3;
        CountDownLatch latch = new CountDownLatch(numberOfChefs);

        // Chefs start preparing the dishes
        Thread one = new Thread(new Chef("Chef A", "Paneer Tikka", latch));
        Thread two = new Thread(new Chef("Chef B", "Butter Chicken", latch));
        Thread three = new Thread(new Chef("Chef C", "Manchurian", latch));

        one.start();
        two.start();
        three.start();

        // Wait for all dishes to be ready
        latch.await();

        System.out.println("\nAll Dishes are ready to be served");
    }
}

class Chef implements Runnable{
    private final String name;
    private final String dish;
    private final CountDownLatch latch;

    public Chef(String name, String dish, CountDownLatch latch){
        this.name = name;
        this.dish = dish;
        this.latch = latch;
    }

    @Override
    public void run(){
        // Simulating the dish preparation
        try {
            System.out.println(name + " is preparing " + dish);
            Thread.sleep(5000);
            System.out.println(name + " has finished preparing " + dish);
            latch.countDown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}