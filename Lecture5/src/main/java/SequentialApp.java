public class SequentialApp {

    public static void main(String[] args) {
        int i = 1;
        int i2 = i * 5;

        System.out.println(i2);

        System.out.println(Thread.currentThread().getName());
    }


}
