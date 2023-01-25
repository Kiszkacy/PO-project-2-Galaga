package galaga.engine.spawner;

import galaga.constants.MovementTypeIdentifier;
import galaga.gameObjects.enemies.*;
import galaga.interfaces.Placeable;
import galaga.movementTypes.LinearMovement;
import galaga.movementTypes.MovementType;
import galaga.movementTypes.SinusoidalMovement;
import galaga.singletons.ShipHandler;
import galaga.util.Color;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static galaga.constants.Global.*;
import static galaga.util.EasyPrint.pcol;

public class WaveSpawner implements Placeable {

    protected PVector position = new PVector(0,0);
    protected ArrayList<WaveSpawn> spawns;
    private int worldWidth;
    private int worldHeight;
    // LOGIC
    protected float spawnCooldown = 1.0f; // initial cooldown
    protected float time = 0.0f;
    protected int shipSpawnIndex = 0;
    protected WaveSpawn activeSpawn = null;


    public void process(float delta) {
        this.spawnCooldown -= delta;
        this.time += delta;
        if (this.activeSpawn == null && this.spawnCooldown <= 0.0f) this.pickSpawn();
        if (this.spawnCooldown <= 0.0f) this.spawn();
    }


    private void pickSpawn() {
        LinkedList<Integer> tickets = new LinkedList<>();
        for(int i = 0; i < this.spawns.size(); i++) {
            WaveSpawn spawn = this.spawns.get(i);
            if (spawn.getActiveFrom() > this.time || spawn.getActiveUntil() < this.time) continue;
            for(int j = 0; j < this.spawns.get(i).getTickets(); j++) {
                tickets.add(i);
            }
        }
        int picked = tickets.get(new Random().nextInt(tickets.size()));
        this.activeSpawn = this.spawns.get(picked);
//        pcol(Color.VIOLET, this.activeSpawn.getSpawns().size());
        this.position.x = this.activeSpawn.getBorders().x + new Random().nextInt(this.worldWidth -
                        (int)this.activeSpawn.getBorders().y - (int)this.activeSpawn.getBorders().x);
    }


    public void spawn() { // SKIPPING BUFFED CHANCE CURRENTLY !!
        if (this.activeSpawn.getSpawns().size() == this.shipSpawnIndex) {
            this.activeSpawn = null;
            this.shipSpawnIndex = 0;
            float minutes = Math.min(Math.max(time-spawnerScalingStart, 0.0f), (spawnerScalingStop-spawnerScalingStart)) / 60.0f;
            this.spawnCooldown = initialSpawnCooldown - spawnScalingPerMinute * minutes;
            return;
        }

//        pcol(Color.WEAK_VIOLET, this.activeSpawn.getSpawns().get(this.shipSpawnIndex).enemyType);
//        pcol(Color.WEAK_VIOLET, this.activeSpawn.getSpawns().get(this.shipSpawnIndex).movementTypeIdentifier);
        // movement type
        boolean sinusoidal = this.activeSpawn.getSpawns().get(this.shipSpawnIndex).movementTypeIdentifier == MovementTypeIdentifier.SINUSOIDAL;
        // enemy type & buffed
        Enemy enemy = null;
        boolean buffed = new Random().nextFloat() <= this.activeSpawn.getSpawns().get(this.shipSpawnIndex).buffedChance;
        switch (this.activeSpawn.getSpawns().get(this.shipSpawnIndex).enemyType) {
            case SMALL -> enemy = buffed ? new SmallBuffed(this.time, sinusoidal) : new Small(this.time, sinusoidal);
            case SHOOTER -> enemy = buffed ? new ShooterBuffed(this.time) : new Shooter(this.time);
            case TANKY -> enemy = buffed ? new TankyBuffed(this.time, sinusoidal) : new Tanky(this.time, sinusoidal);
            case SPEEDY -> enemy = buffed ? new SpeedyBuffed(this.time) : new Speedy(this.time);
        }

        enemy.launch(this.position.copy().add(this.activeSpawn.getSpawns().get(this.shipSpawnIndex).offset), new PVector(0,1), enemy.getLaunchSpeed());
        ShipHandler.registerShip(enemy);
        this.spawnCooldown = this.activeSpawn.getSpawns().get(this.shipSpawnIndex).delay;
        this.shipSpawnIndex++;
        if (this.spawnCooldown <= 0.0) this.spawn();
    }

    // constructors

    public WaveSpawner(PVector position, ArrayList<WaveSpawn> spawns, int worldWidth, int worldHeight) {
        this.position = position;
        this.spawns = spawns;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    // getters/setters

    /**
     * @return
     */
    @Override
    public PVector getPosition() {
        return this.position;
    }

    /**
     * @param position
     */
    @Override
    public void setPosition(PVector position) {
        this.position = position;
    }
}
