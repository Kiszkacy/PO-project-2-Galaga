package galaga.engine.spawner;

import galaga.constants.EnemyType;
import galaga.constants.MovementTypeIdentifier;
import galaga.util.ExceptionHandler;
import galaga.util.Trio;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import processing.core.PVector;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class WaveSpawn {

    private float activeFrom;
    private float activeUntil;
    private PVector borders;
    private int tickets;
    private ArrayList<EnemySpawn> spawns;

    public static WaveSpawn load(String path) {
        try {
            // create json parser
            JSONParser parser = new JSONParser();
            // look for file
            if (!new File(path).exists() || new File(path).isDirectory())
                throw new RuntimeException(String.format("spawn wave file: '%s' can not be found'", path));
            // create JSONObject instance
            JSONObject obj = (JSONObject)parser.parse(new FileReader(path));
            // load
            JSONArray activeTime = (JSONArray)obj.get("activeTime");
            float activeFrom = (float)(double)activeTime.get(0);
            float activeUntil = activeTime.get(1) == null ? Float.POSITIVE_INFINITY : (float)(double)activeTime.get(1);
            JSONArray _borders = (JSONArray)obj.get("borders");
            PVector borders = new PVector((int)(long)_borders.get(0), (int)(long)_borders.get(1));
            int tickets = (int)(long)obj.get("tickets");
            JSONArray wave = (JSONArray)obj.get("wave");
            ArrayList<EnemySpawn> spawns = new ArrayList<>();
            for(Object o : wave) {
                JSONObject jo = ((JSONObject)o);
                EnemyType type = EnemyType.valueOf((String)jo.get("type"));
                float buffedChance = (float)(double)jo.get("buffedChance");
                float delay = (float)(double)jo.get("delay");
                MovementTypeIdentifier movementTypeIdentifier = MovementTypeIdentifier.valueOf((String)jo.get("movement"));
                JSONArray _offset = (JSONArray)jo.get("offset");
                PVector offset = new PVector((int)(long)_offset.get(0), (int)(long)_offset.get(1));

                spawns.add(new EnemySpawn(type, buffedChance, delay, movementTypeIdentifier, offset));
            }

            return new WaveSpawn(activeFrom, activeUntil, borders, tickets, spawns);
        }  catch (Exception e) {
            ExceptionHandler.printCriticalInfo(e);
        }
        return null; // should never happen
    }

    // constructors

    public WaveSpawn(float activeFrom, float activeUntil, PVector borders, int tickets, ArrayList<EnemySpawn> spawns) {
        this.activeFrom = activeFrom;
        this.activeUntil = activeUntil;
        this.borders = borders;
        this.tickets = tickets;
        this.spawns = spawns;
    }

    // getters/setters


    public float getActiveFrom() {
        return this.activeFrom;
    }

    public float getActiveUntil() {
        return this.activeUntil;
    }

    public PVector getBorders() {
        return this.borders;
    }

    public int getTickets() {
        return this.tickets;
    }

    public ArrayList<EnemySpawn> getSpawns() {
        return this.spawns;
    }
}
