import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PassingDataApp {

    private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);


    public static void main(String[] args) {
        Thread supplierThread = new Thread(() -> {
            while (true) {
                try {
                    var s = "hello world";
                    System.out.println(Thread.currentThread().getName() + " sent: " + s);

                    queue.put(s);

                    Thread.sleep(40000);
                }
                catch (Exception e) {}
            }
        });

        Thread consumerThread = new Thread(() -> {
            while (true) {
                try {
                    var s = queue.take();

                    System.out.println(Thread.currentThread().getName() + " received: " + s);
                }
                catch (Exception e) {}
            }
        });

        supplierThread.setDaemon(true);
        consumerThread.setDaemon(true);

        supplierThread.start();
        consumerThread.start();

        var scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
