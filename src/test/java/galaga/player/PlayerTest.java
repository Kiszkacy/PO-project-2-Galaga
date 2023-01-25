package galaga.player;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import galaga.gameObjects.PlayerShip;

public class PlayerTest {

    @Test
    void scoreTest() {
        float[][] scores = {
                {0.5f, 1.0f, 20.0f},
                {-20.0f, 10.0f},
                {50.0f, -40.0f, 1.0f, 0.1f},
                {25.0f, 100.0f, -200.0f, 10.0f},
                {4.0f, 5.0f, 6.0f},
                {50.0f, -1.0f, 2.0f},
        };
        float[][] shipHealth = {
                {1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f},
                {0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 1.0f},
        };
        float[] expectedResults = {
                21.5f,
                10.0f,
                11.1f,
                10.f,
                5.0f,
                2.0f
        };

        for(int i = 0; i < scores.length; i++) {
            Player player = new Player("nickname");
            for(int j = 0; j < scores[i].length; j++) {
                player.setShip(new PlayerShip());
                player.getShip().setHealth(shipHealth[i][j]);
                player.addScore(scores[i][j]);
            }
            assertEquals(expectedResults[i], player.getScore(), 1e-6);
        }
    }
}
