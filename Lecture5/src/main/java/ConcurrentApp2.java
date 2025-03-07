import java.util.concurrent.CountDownLatch;

public class ConcurrentApp2 {

    public static void main(String[] args) throws Exception {

        CountDownLatch latch = new CountDownLatch(3);

        for (int i=0; i<3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                latch.countDown();
            }).start();
        }

        int i = 1;
        int i2 = i * 5;

        latch.await();

        System.out.println(i2);
        System.out.println(Thread.currentThread().getName());

    }

}
