package org.example;

import java.util.List;
import java.util.Random;

public class ParallelMain {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());

        List<String> items = List.of("A", "B", "C", "D");


        items.parallelStream()
                .forEach(s -> {
                    try {
                        Thread.sleep(new Random().nextInt(500));
                    }
                    catch (InterruptedException e) {}

                    System.out.println(s);

                });
    }
}
