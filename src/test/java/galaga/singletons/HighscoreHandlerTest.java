package galaga.singletons;

import galaga.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HighscoreHandlerTest {

    @Test
    void registerTest() {
        String[] nickname = {
                "ABC",
                "ABC",
                "DEF",
                "GHI",
                "JKL",
                "MNO",
        };
        int[] score = {
                250,
                0,
                100,
                10000,
                5000,
                10,
        };
        List<Pair<String, Integer>> expectedResults = new ArrayList<>();
        expectedResults.add(new Pair<>("GHI", 10000));
        expectedResults.add(new Pair<>("JKL", 5000));
        expectedResults.add(new Pair<>("ABC", 250));
        expectedResults.add(new Pair<>("DEF", 100));
        expectedResults.add(new Pair<>("MNO", 10));
        expectedResults.add(new Pair<>("ABC", 0));

        HighscoreHandler.init();
        for(int i = 0; i < nickname.length; i++) {
            HighscoreHandler.registerScore(nickname[i], score[i], false);
        }

        for(int i = 0; i < HighscoreHandler.getScores().size(); i++) {
            assertEquals(expectedResults.get(i), HighscoreHandler.getScores().get(i));
        }
    }
}
