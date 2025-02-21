package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        CustomString cs = new CustomString();

        for (var f: cs.getClass().getDeclaredFields()) {
            f.set(cs, "恭喜发财");
            System.out.println(f);
        }

        System.out.println(cs.s);
    }
}
