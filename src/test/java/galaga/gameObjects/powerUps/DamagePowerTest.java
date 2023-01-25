package galaga.gameObjects.powerUps;

import galaga.gameObjects.Ship;
import galaga.gameObjects.enemies.Shooter;
import galaga.gameObjects.powerups.DamagePower;
import galaga.gameObjects.powerups.HealthPower;
import galaga.gameObjects.powerups.PowerUp;
import galaga.guns.player.BasicGun;
import galaga.singletons.SpriteHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DamagePowerTest {

    @Test
    void activateTest() {
        SpriteHandler.init();

        Ship[] ship = {
            new Shooter(),
            new Shooter(),
            new Shooter(),
            new Shooter(),
        };
        float[] startingMultiplier = {
            1.0f,
            1.5f,
            0.0f,
            3.0f,
        };
        float[][] expectedResults = {
            {1.5f, 2.0f, 2.5f, 3.0f},
            {2.0f, 2.5f, 3.0f, 3.5f},
            {0.5f, 1.0f, 1.5f, 2.0f},
            {3.5f, 4.0f, 4.5f, 5.0f},
        };

        for(int i = 0; i < ship.length; i++) {
            ship[i].setGun(new BasicGun());
            ship[i].getGun().addDamageMultiplier(startingMultiplier[i]-1.0f);
            PowerUp power = new DamagePower();
            for(int j = 0; j < expectedResults[i].length; j++) {
                power.activateOn(ship[i]);
                assertEquals(expectedResults[i][j], ship[i].getGun().getDamageMultiplier());
            }
        }
    }
}
