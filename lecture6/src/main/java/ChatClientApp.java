import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.net.Client;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

// intellij
// configuration, edit, modify options, allow multiple instances
public class ChatClientApp extends GameApplication {

    private Client<Bundle> client;
    private List<String> history = new ArrayList<>();

    @Override
    protected void initSettings(GameSettings gameSettings) { }

    @Override
    protected void initUI() {
        FXGL.getGameScene().setBackgroundColor(Color.GREENYELLOW);

        BorderPane rootPane = new BorderPane();

        // 输入框，发送信息
        TextField textInput = new TextField();
        textInput.setFont(Font.font(24));
        textInput.setOnAction(evt -> {
            // serialization
            var bundle = new Bundle("Data");
            bundle.put("message", textInput.getText());

            textInput.clear();
            client.broadcast(bundle);
        });

        // 显示聊天
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);

        // 搜索聊天记录
        TextField fieldSearch = new TextField();
        fieldSearch.setOnAction(evt -> {
            // 方案1和3一定创建了new thread，不太好

            // 1
//            new Thread(() -> {
//                doSearch(fieldSearch.getText(), chatArea);
//            }).start();

            // 2
            FXGL.getExecutor().startAsync(() -> {
                doSearch(fieldSearch.getText(), chatArea);
            });

            // 3
//            new SearchThread(chatArea, fieldSearch.getText()).start();

            // 4
//            Executors.newCachedThreadPool().submit(() -> {
//                doSearch(fieldSearch.getText(), chatArea);
//            });

        });


        rootPane.setTop(fieldSearch);
        rootPane.setCenter(new VBox(chatArea, textInput));
        FXGL.addUINode(rootPane);

        client = FXGL.getNetService().newTCPClient("localhost", 50000);
        client.setOnConnected(connection -> {
            connection.addMessageHandlerFX((conn, bundle) -> {
                // handle获取信息
                String data = bundle.get("message");
                history.add(data);
                chatArea.appendText(data + "\n");
            });

        });

        // 这句有问题，会freeze ui
//        client.connectTask().run();

        client.connectAsync();
    }

    private void doSearch(String searchTerm, TextArea chatArea) {
        StringBuilder result = new StringBuilder();

        history.stream()
                .filter(line -> line.contains(searchTerm))
                .forEach(line -> {
                    result.append(line).append("\n");
                });

        FXGL.getExecutor().startAsync(() -> {
            chatArea.clear();
            chatArea.appendText(result.toString());

        });
    }

    private class SearchThread extends Thread {
        private String searchTerm;
        private TextArea chatArea;

        public SearchThread(TextArea chatArea, String searchTerm) {
            this.chatArea = chatArea;
            this.searchTerm = searchTerm;
        }

        @Override
        public void run() {
            doSearch(searchTerm, chatArea);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}