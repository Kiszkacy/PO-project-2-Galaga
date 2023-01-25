package galaga.util;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Singleton pattern. Loaded once at the start. Holds all information about input settings.
 */
public class InputsConfig {

    private static final InputsConfig instance = new InputsConfig();
    private static final String version = "1.0"; // current version of config file
    private ArrayList<String> rightKey;
    private ArrayList<String> leftKey;
    private ArrayList<String> upKey;
    private ArrayList<String> downKey;
    private ArrayList<String> shootKey;
    private ArrayList<String> acceptKey;


    public static void load(String from) throws Exception {
        // create json parser
        JSONParser parser = new JSONParser();
        // look for file
        String path = from + ".json";
        if (!new File(path).exists() || new File(path).isDirectory()) throw new RuntimeException(String.format("inputs config file: '%s' can not be found in '%s'", from, path));
        // create JSONObject instance
        JSONObject obj = (JSONObject) parser.parse(new FileReader(path));
        // check file version
        if (!version.equals(obj.get("version"))) throw new RuntimeException(String.format("outdated inputs config file, version: '%s', expected: '%s'", obj.get("version"), version));
        // load settings
        JSONObject keymap = (JSONObject) obj.get("keymap");

        instance.rightKey = new ArrayList<>();
        JSONArray rightKey = (JSONArray) keymap.get("right");
        rightKey.forEach(key -> instance.rightKey.add((String)key));

        instance.leftKey = new ArrayList<>();
        JSONArray leftKey = (JSONArray) keymap.get("left");
        leftKey.forEach(key -> instance.leftKey.add((String)key));

        instance.upKey = new ArrayList<>();
        JSONArray upKey = (JSONArray) keymap.get("up");
        upKey.forEach(key -> instance.upKey.add((String)key));

        instance.downKey = new ArrayList<>();
        JSONArray downKey = (JSONArray) keymap.get("down");
        downKey.forEach(key -> instance.downKey.add((String)key));

        instance.shootKey = new ArrayList<>();
        JSONArray shootKey = (JSONArray) keymap.get("shoot");
        shootKey.forEach(key -> instance.shootKey.add((String)key));

        instance.acceptKey = new ArrayList<>();
        JSONArray acceptKey = (JSONArray) keymap.get("accept");
        acceptKey.forEach(key -> instance.acceptKey.add((String)key));
    }

    // overrides

    @Override
    public String toString() {
        return String.format("CONFIG: | %s %s %s %s %s |",
                version, instance.rightKey, instance.leftKey, instance.shootKey, instance.acceptKey);
    }

    // constructors

    private InputsConfig() { // no one is able to create another config instance

    }

    // getters/setters

    public static String getVersion() {
        return version;
    }


    public static ArrayList<String> getLeftKey() {
        return instance.leftKey;
    }


    public static ArrayList<String> getRightKey() {
        return instance.rightKey;
    }


    public static ArrayList<String> getShootKey() {
        return instance.shootKey;
    }


    public static ArrayList<String> getAcceptKey() {
        return instance.acceptKey;
    }

    public static ArrayList<String> getUpKey() {
        return instance.upKey;
    }

    public static ArrayList<String> getDownKey() {
        return instance.downKey;
    }
}

