package org.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
//        BlockingQueue<String> lock = new ArrayBlockingQueue<>(1);
        CountDownLatch latch = new CountDownLatch(1);

        Runnable function = () -> {
            System.out.println("Hello from a background thread: " + Thread.currentThread().getName());

            try {
//                lock.put("Pill");

                latch.countDown();
            }
            catch (Exception e) {}
        };

        var thread = new Thread(function);
        thread.start();

        try {
//            String result = lock.take();

            latch.await();

            System.out.println("Hello from the main thread");
        }
        catch (Exception e) {}
    }
}