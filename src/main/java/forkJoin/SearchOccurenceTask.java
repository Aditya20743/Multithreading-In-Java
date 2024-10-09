package forkJoin;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchOccurenceTask extends RecursiveTask<Integer> {
    int[] arr;
    int start;
    int end;
    int searchElement;

    public SearchOccurenceTask(int[] arr, int start, int end, int searchElement){
        this.arr = arr;
        this.start = start;
        this.end = end;
        this.searchElement = searchElement;
    }

    @Override
    protected Integer compute() {
        int size = end-start+1;
        if(size>50){
            int mid = (start+end)/2;
            SearchOccurenceTask task1 = new SearchOccurenceTask(arr, start, mid, searchElement);
            SearchOccurenceTask task2 = new SearchOccurenceTask(arr, mid+1, end, searchElement);

            task1.fork();
            task2.fork();

            return task1.join() + task2.join();
        }

        return search();
    }

    private Integer search() {
        int count = 0;
        for (int i = start; i <= end ; i++) {
            if (arr[i] == searchElement) {
                count++;
            }
        }
        return count;
    }
}

class FJPDemo{
    public static void main(String[] args) {
        int[] array = new int[100];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(10) + 1;
        }

        int searchElement = random.nextInt(10) + 1;

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        try {
            SearchOccurenceTask task = new SearchOccurenceTask(array, 0, array.length-1, searchElement);
            Integer occurrence = pool.invoke(task);
            System.out.println("Array is : " + Arrays.toString(array));
            System.out.printf("%d found %d times", searchElement, occurrence);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
