import java.util.stream.IntStream;

public class ParallelApp {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10)
                .parallel()
                .forEach(i -> {
                    var result = i * i;

                    System.out.println(Thread.currentThread().getName() + " calculated value of: " + result);
                });
    }
}
