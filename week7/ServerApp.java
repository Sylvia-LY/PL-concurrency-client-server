package org.example.week7;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class ServerApp extends Application {
    public static class Launcher {
        public static void main(String[] args) {
            Application.launch(ServerApp.class);
        }
    }

    private TextArea outputTextArea = new TextArea();
    private TextField inputTextField = new TextField();
    private PrintWriter writer = null;

    @Override
    public void start(Stage stage) throws Exception {
        var t = new Thread(() -> {
            startServer();
        });
        t.setDaemon(true);
        t.start();

        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    private Parent createContent() {
        outputTextArea.setFont(Font.font(24));
        outputTextArea.setPrefSize(1280, 720);

        inputTextField.setFont(Font.font(24));
        inputTextField.setOnAction((evt) -> {
            if (this.writer == null) {
                return;
            }

            this.writer.println(inputTextField.getText());
        });

        return new VBox(10, outputTextArea, inputTextField);
    }

    private void startServer() {
        System.out.println("Hello from the server");
        System.out.println("Waiting for an incoming connection");

        try (
                var ss = new ServerSocket(50000);
                var clientSocket = ss.accept();

                // 写data给client
                var writer = new PrintWriter(clientSocket.getOutputStream(), true);
                // 读client发来的data
                var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ) {

            System.out.println("Connection established");

            this.writer = writer;

            String input = "";
            while ((input = reader.readLine()) != null) {
                var temp = "client说：%s%n".formatted(input);
                Platform.runLater(() -> {
                    outputTextArea.appendText(temp);
                });
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Exiting server app");
    }
}