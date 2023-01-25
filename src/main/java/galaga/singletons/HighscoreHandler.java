package galaga.singletons;

import galaga.util.ExceptionHandler;
import galaga.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import processing.core.PImage;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class HighscoreHandler {

    private static final HighscoreHandler instance = new HighscoreHandler();
    private List<Pair<String, Integer>> scores;


    public static void init() {
        instance.scores = new ArrayList<>();
    }


    public static boolean registerScore(String nickname, Integer score, boolean save) {
        instance.scores.add(new Pair<String, Integer>(nickname, score));
        instance.scores.sort((o1, o2) -> {if (o1.second > o2.second) return -1; else return 1;});
        if (save) HighscoreHandler.saveScores();
        return true;
    }


    public static void saveScores() {
        JSONObject save = new JSONObject();
        JSONArray highscores = new JSONArray();
        save.put("highscores", highscores);

        for(Pair<String, Integer> score : instance.scores) {
            JSONObject _score = new JSONObject();
            _score.put("nickname", score.first);
            _score.put("score", score.second);
            highscores.add(_score);
        }
        try {
            FileWriter file = new FileWriter("scores\\highscores.json");
            file.write(save.toJSONString());
            file.close();
        } catch (Exception e) {
            ExceptionHandler.printCriticalInfo(e);
        }
    }

    // constructors

    private HighscoreHandler() { // no one is able to create another instance of this object

    }

    // getters/setters

    public static List<Pair<String, Integer>> getScores() {
        return instance.scores;
    }
}
