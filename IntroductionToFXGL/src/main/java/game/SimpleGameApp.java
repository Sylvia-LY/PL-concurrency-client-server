package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class SimpleGameApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
    }

    @Override
    protected void initInput() {
        onKeyDown(KeyCode.F, () -> {
            getNotificationService().pushNotification("恭喜发财");
        });
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new SimpleFactory());

        run(() -> {
            // random points within bounds
            spawn("enemy", FXGLMath.randomPoint(
                    new Rectangle2D(0, 0, getAppWidth(), getAppHeight())
            ));

            spawn("ally", FXGLMath.randomPoint(
                    new Rectangle2D(0, 0, getAppWidth(), getAppHeight())
            ));

        }, Duration.seconds(1));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
