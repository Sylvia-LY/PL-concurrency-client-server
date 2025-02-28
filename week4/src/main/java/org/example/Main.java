package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {

        // 1 generic types -
        // eager will run here（至此都还是null，supplier尚未执行）
        LazyObject<Integer> obj1 = new LazyObject<>(() -> 1234);
        LazyObject<String> obj2 = new LazyObject<>(() -> "恭喜发财");

        // lazy will run here
        System.out.println(obj1.getValue());
        System.out.println(obj2.getValue());


        // 2 metaprogramming / annotations / reflection -
        SomeApp app = SomeApp.class.getDeclaredConstructor().newInstance();
//        app.start();

        for (var method : SomeApp.class.getDeclaredMethods()) {
//            System.out.println(method);

//            method.invoke(app);


            // method.getDeclaredAnnotations() - Annotation[]
            // ↓是这个array的内存地址 - call on array
            System.out.println(method.getDeclaredAnnotations());

            // ↓content - call on each element of the array
            System.out.println(Arrays.toString(
                    method.getDeclaredAnnotations()

            ));


        }
    }
}
