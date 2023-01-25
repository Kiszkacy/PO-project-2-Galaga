package galaga.gameObjects.powerUps;

import galaga.gameObjects.PlayerShip;
import galaga.gameObjects.Ship;
import galaga.gameObjects.enemies.Shooter;
import galaga.gameObjects.powerups.MachinePower;
import galaga.gameObjects.powerups.PowerUp;
import galaga.gameObjects.powerups.ShotgunPower;
import galaga.guns.player.BasicGun;
import galaga.guns.player.MachineGun;
import galaga.guns.player.ShotGun;
import galaga.singletons.SpriteHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShotgunPowerTest {

    @Test
    void activateTest() {
        SpriteHandler.init();

        Ship[] ship = {
            new PlayerShip(),
            new PlayerShip(),
            new PlayerShip(),
            new Shooter(),
            new Shooter(),
            new Shooter(),
        };
        for(int i = 0; i < ship.length; i++) {
            ship[i].setGun(new BasicGun());
            ship[i].getGun().addDamageMultiplier(0.5f*(i%3));
        }

        List<ShotGun> expectedResults = new ArrayList<>();
        for(int i = 0; i < ship.length; i++) {
            expectedResults.add(new ShotGun());
            expectedResults.get(i).addDamageMultiplier(0.5f*(i%3));
        }

        for(int i = 0; i < ship.length; i++) {
            PowerUp power = new ShotgunPower();
            power.activateOn(ship[i]);
            assertEquals(expectedResults.get(i), ship[i].getGun());
        }
    }
}
