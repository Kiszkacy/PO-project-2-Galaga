package galaga.gameObjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerShipTest {

    @Test
    void healTest() {
        float[][] amount = {
            {1.0f, 1.0f},
            {2.0f},
            {6.0f},
            {1.0f, 1.0f, 4.0f},
            {2.0f, 2.0f, 2.0f, 2.0f},
        };
        float[] startingHealth = {
            1.0f,
            2.0f,
            3.0f,
            6.0f,
            5.0f
        };
        float[] expectedResults = {
            3.0f,
            4.0f,
            6.0f,
            6.0f,
            6.0f
        };

        for(int i = 0; i < amount.length; i++) {
            PlayerShip player = new PlayerShip();
            player.setHealth(startingHealth[i]);
            for(int j = 0; j < amount[i].length; j++) {
                player.heal(amount[i][j]);
            }
            assertEquals(expectedResults[i], player.getHealth(), 1e-6);
        }
    }

    @Test
    void damageTest() {
        float[][] amount = {
            {1.0f, 1.0f},
            {2.0f, 1.0f},
            {2.0f},
            {1.0f, 1.0f, 4.0f},
            {2.0f, 2.0f, 2.0f, 2.0f},
        };
        float[] startingHealth = {
            3.0f,
            3.0f,
            1.0f,
            6.0f,
            5.0f
        };
        float[] expectedResults = {
            1.0f,
            0.0f,
            -1.0f,
            0.0f,
            -3.0f
        };

        for(int i = 0; i < amount.length; i++) {
            PlayerShip player = new PlayerShip();
            player.setHealth(startingHealth[i]);
            for(int j = 0; j < amount[i].length; j++) {
                player.damage(amount[i][j]);
            }
            assertEquals(expectedResults[i], player.getHealth(), 1e-6);
        }
    }
}
