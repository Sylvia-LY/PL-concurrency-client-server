package org.example;

import java.util.concurrent.Executors;

public class AnotherMain {
    public static void main(String[] args) throws Exception {

        // 线程池 - 创建thread、复用空闲thread（执行完任务不会立马报废而是等待新任务）
        var service = Executors.newCachedThreadPool();

        // 异步 - 不会阻塞main thread
        service.submit(() -> {
            System.out.println("Name: " + Thread.currentThread().getName());
        });

        // 防止main thread过早结束，线程池任务没干完。。
        Thread.sleep(5000);

    }
}
