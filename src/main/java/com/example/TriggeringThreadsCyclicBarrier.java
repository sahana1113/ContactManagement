//package com.example;
//
//import java.util.concurrent.CyclicBarrier;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class TriggeringThreadsCyclicBarrier {
//	public static void main(String[] args) throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(1000);
//        CyclicBarrier barrier = new CyclicBarrier(1000, new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("All threads are ready to start!");
//            }
//        });
//        for (int i = 0; i < 1000; i++) {
//            executorService.submit(new MyTask(i, barrier));
//        }
//        executorService.shutdown();
//    }
//}
//class MyTask implements Runnable {
//    private final int taskId;
//    private final CyclicBarrier barrier;
//
//    public MyTask(int taskId, CyclicBarrier barrier) {
//        this.taskId = taskId;
//        this.barrier = barrier;
//    }
//
//    @Override
//    public void run() {
//        try {
//            barrier.await(); 
//        } catch (Exception e) {
//            Thread.currentThread().interrupt(); 
//        }
//    }
//}


