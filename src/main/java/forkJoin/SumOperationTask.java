package forkJoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumOperationTask extends RecursiveTask<Integer> {

    int[] arr;
    int start;
    int end;

    public SumOperationTask(int[] arr, int start, int end){
        this.arr = arr;
        this.start = start;
        this.end = end;
    }
    @Override
    protected Integer compute() {
        int size = end-start+1;
        if(size==1){
            return arr[start];
        }
        int mid = start + (end-start)/2;
        SumOperationTask task1 = new SumOperationTask(arr, start, mid);
        SumOperationTask task2 = new SumOperationTask(arr, mid+1, end);
        task1.fork();
        task2.fork();

        return task1.join() + task2.join();
    }

    public static void main(String[] args) {
        int[]arr = new int[1000000];
        for(int i=0;i<arr.length;i++){
            arr[i] = i+1;
        }
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        try {
            SumOperationTask task = new SumOperationTask(arr, 0, arr.length-1);
            Integer sum = pool.invoke(task);
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            System.out.println("Array is : " + Arrays.toString(arr));
            System.out.printf("%d is the sum of elements of Array", sum);
            System.out.println("Time taken: " + timeTaken + " ms");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
