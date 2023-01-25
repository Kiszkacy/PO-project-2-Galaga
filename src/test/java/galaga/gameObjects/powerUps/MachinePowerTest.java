package galaga.gameObjects.powerUps;

import galaga.gameObjects.PlayerShip;
import galaga.gameObjects.Ship;
import galaga.gameObjects.enemies.Shooter;
import galaga.gameObjects.enemies.Small;
import galaga.gameObjects.powerups.MachinePower;
import galaga.gameObjects.powerups.PowerUp;
import galaga.guns.player.BasicGun;
import galaga.guns.player.MachineGun;
import galaga.singletons.SpriteHandler;
import galaga.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MachinePowerTest {

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

        List<MachineGun> expectedResults = new ArrayList<>();
        for(int i = 0; i < ship.length; i++) {
            expectedResults.add(new MachineGun());
            expectedResults.get(i).addDamageMultiplier(0.5f*(i%3));
        }

        for(int i = 0; i < ship.length; i++) {
            PowerUp power = new MachinePower();
            power.activateOn(ship[i]);
            assertEquals(expectedResults.get(i), ship[i].getGun());
        }
    }
}
