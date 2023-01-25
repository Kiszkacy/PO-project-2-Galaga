package galaga.engine.spawner;


import java.io.File;
import java.util.ArrayList;

public class WaveSpawnLoader {

    static final File spawnDirectory = new File("src\\main\\resources\\galaga\\spawnWaves");
    private ArrayList<WaveSpawn> waves = new ArrayList<>();


    public void load() {
        for (final File f : spawnDirectory.listFiles()) {
            this.waves.add(WaveSpawn.load(f.getAbsolutePath()));
        }
    }

    // constructors

    public WaveSpawnLoader() {

    }

    // getters/setters

    public ArrayList<WaveSpawn> getWaves() {
        return this.waves;
    }
}
