package game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SimpleFactory implements EntityFactory {

    // 刷怪的时候call的函数
    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        // loading textures
        var texture = FXGL.texture("skull.1024x1024.png", 100, 100).multiplyColor(Color.LIMEGREEN);

        return FXGL.entityBuilder(data)
//                .view(new Rectangle(40, 40, Color.RED))
                .view(texture)
                .with(new ProjectileComponent(new Point2D(1, 0), 150))
                .build();
    }


    @Spawns("ally")
    public Entity newAlly(SpawnData data) {
        var texture = FXGL.texture("dino.878x1024.png", 60, 60);

        return FXGL.entityBuilder(data)
                .view(texture)
                .with(new ProjectileComponent(new Point2D(-1, 0), 150))
                .build();
    }
}
