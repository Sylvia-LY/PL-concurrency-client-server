package org.example.week7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        System.out.println("Hello from the client");

        try (
                // 创建client socket，自动发起连接尝试
                Socket socket = new Socket("localhost", 50000);

                // 用来往server发送data
                var writer = new PrintWriter(socket.getOutputStream(), true);
                // 用来从server接收data
                var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                var scanner = new Scanner(System.in);
                ) {

            // read from network thread (read)
            var t = new Thread(() -> {
                try {
                    String input = "";
                    while ((input = reader.readLine()) != null) {
                        System.out.printf("server说：%s%n", input);
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
            t.setDaemon(true);
            t.start();

            // read from terminal thread (write)
            while (true) {
                String input = scanner.nextLine();
                writer.println(input);

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Exiting client app");
    }
}