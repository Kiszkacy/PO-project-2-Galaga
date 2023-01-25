package galaga.gameObjects.powerUps;

import galaga.gameObjects.Ship;
import galaga.gameObjects.enemies.Shooter;
import galaga.gameObjects.powerups.HealthPower;
import galaga.gameObjects.powerups.PowerUp;
import galaga.singletons.SpriteHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HealthPowerTest {

    @Test
    void activateTest() {
        SpriteHandler.init();

        Ship[] ship = {
            new Shooter(),
            new Shooter(),
            new Shooter(),
            new Shooter(),
        };
        float[] startingHealth = {
            1.0f,
            2.0f,
            2.0f,
            2.0f,
        };
        float[] maxHealth = {
            3.0f,
            3.0f,
            2.0f,
            7.0f,
        };
        float[][] expectedResults = {
            {2.0f, 3.0f, 3.0f, 3.0f},
            {3.0f, 3.0f, 3.0f, 3.0f},
            {2.0f, 2.0f, 2.0f, 2.0f},
            {3.0f, 4.0f, 5.0f, 6.0f},
        };

        for(int i = 0; i < ship.length; i++) {
            ship[i].setMaxHealth(maxHealth[i]);
            ship[i].setHealth(startingHealth[i]);
            PowerUp power = new HealthPower();
            for(int j = 0; j < expectedResults[i].length; j++) {
                power.activateOn(ship[i]);
                assertEquals(expectedResults[i][j], ship[i].getHealth());
            }
        }
    }
}
