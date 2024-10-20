package com.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TriggeringThreadsLatch {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new MyTask(i, latch));
        }
        
        latch.countDown();
        executorService.shutdown();
    }
}

class MyTask implements Runnable {
    private final int taskId;
    private final CountDownLatch latch;

    public MyTask(int taskId, CountDownLatch latch) {
        this.taskId = taskId;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        }
    }
}

