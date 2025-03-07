import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class FXGLConcurrencyApp extends GameApplication {


    @Override
    protected void initSettings(GameSettings gameSettings) {}

    @Override
    protected void initUI() {
        Button btn = new Button("Run Async");
        btn.setFont(Font.font(36));

        btn.setOnAction((evt) -> {
            FXGL.getExecutor().startAsync(() -> doHeavyWork());
        });

        FXGL.addUINode(btn);
    }

    private void doHeavyWork() {
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
