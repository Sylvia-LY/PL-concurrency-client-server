import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;

public class ChatServerApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) { }

    @Override
    protected void initUI() {
        // networking

        var server = FXGL.getNetService().newTCPServer(50000);
        server.setOnConnected(connection -> {
            System.out.println("New connection: " + connection);
            String userId = connection.toString().substring(connection.toString().indexOf("@") + 1);

            connection.addMessageHandler((conn, bundle) -> {
                String data = bundle.get("message");
                System.out.println("Received from %s: %s".formatted(userId, data));

                var newBundle = new Bundle("Data");
                newBundle.put("message", "%s: %s".formatted(userId, data));

                server.getConnections().forEach(c -> {
                    // 不应该send原bundle，should be new bundle
                    // 原因：没筛选，没加密

                    c.send(newBundle);
                });

            });
        });

        server.startAsync();
    }

    public static void main(String[] args) {
        launch(args);
    }
}