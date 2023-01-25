package galaga.engine;

import galaga.constants.Global;
import galaga.constants.Layers;
import galaga.constants.PowerUpType;
import galaga.engine.spawner.WaveSpawnLoader;
import galaga.engine.spawner.WaveSpawner;
import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.Entity;
import galaga.gameObjects.PlayerShip;
import galaga.gameObjects.Ship;

import galaga.gameObjects.enemies.Enemy;
import galaga.gameObjects.powerups.PowerUp;
import galaga.gameObjects.projectiles.Projectile;
import galaga.guns.player.BasicGun;
import galaga.guns.player.MachineGun;
import galaga.guns.player.ShotGun;
import galaga.interfaces.Damageable;
import galaga.interfaces.Drawable;
import galaga.interfaces.Processable;
import galaga.observer.*;
import galaga.player.Player;
import galaga.singletons.HighscoreHandler;
import galaga.singletons.PowerUpHandler;
import galaga.singletons.ProjectileHandler;
import galaga.singletons.ShipHandler;
import galaga.util.Color;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static galaga.constants.Global.*;
import static galaga.util.EasyPrint.p;
import static galaga.util.EasyPrint.pcol;

public class Engine implements DestroyObserver, HitObserver, Drawable, Processable {

    private Player player;
    private PlayerShip playerShip;
    private InputHandler inputHandler;
    private CollisionHandler collisionHandler;
    private int worldWidth;
    private int worldHeight;
    private LinkedList<WaveSpawner> spawners;
    private WaveSpawnLoader enemySpawnLoader;
    private LinkedList<AnimatedSprite> explosions = new LinkedList<>();
    private float powerUpTimer = 0.0f;
    private float gunPowerUpTimer = 0.0f;
    private PowerUpType powerUpType;


    public void init(InputHandler inputHandler, int worldWidth, int worldHeight) {
        this.inputHandler = inputHandler;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        ProjectileHandler.init();
        ProjectileHandler.getInstance().addObserver(this);
        ShipHandler.init();
        ShipHandler.getInstance().addObserver(this);
        PowerUpHandler.init();
        PowerUpHandler.getInstance().addObserver(this);
        collisionHandler.addObserver(this);

        // TMP
        this.playerShip = new PlayerShip(new PVector(this.worldWidth/2-32, this.worldHeight-92), new PVector(0,0), new PVector(32, 48),
                                        new ArrayList<>(), 3.0f, 640.0f, 8192.0f, 24.0f, new BasicGun(), new PVector(0, -1), "player", 15);
        this.player.setShip(this.playerShip);
        ShipHandler.registerShip(this.playerShip);
        this.inputHandler.addObserver(this.playerShip);

        this.enemySpawnLoader = new WaveSpawnLoader();
        this.enemySpawnLoader.load();
        this.spawners = new LinkedList<>();
        this.spawners.add(new WaveSpawner(new PVector(0,-spawnerOffset), this.enemySpawnLoader.getWaves(), this.worldWidth, this.worldHeight));
    }


    public void process(float delta) {
        ProjectileHandler.process(delta);
        ShipHandler.process(delta);
        PowerUpHandler.process(delta);
        // player process
        if (this.playerShip.getHealth() > 0) { // stop processing player when dead
            this.player.addScore(delta);
            PVector playerPos = this.playerShip.getPosition();
            // clamp player pos
            if (playerPos.x <= 0.0f || playerPos.x >= worldWidth - this.playerShip.getBoundary().x ||
                    playerPos.y <= 0.0f || playerPos.y >= worldHeight - this.playerShip.getBoundary().y) {
                this.playerShip.setVelocity(new PVector(0, 0));
            }
            this.playerShip.setPosition(new PVector(Math.max(0.0f, Math.min(worldWidth - this.playerShip.getBoundary().x, playerPos.x)),
                    Math.max(0.0f, Math.min(worldHeight - this.playerShip.getBoundary().y, playerPos.y))));
        }
        // tp enemies to the top
        List<Ship> enemies = ShipHandler.getShips().get(Layers.ENEMY.id());
        for (Ship s : enemies) {
            if (s.getPosition().y > worldHeight) s.setPosition(s.getPosition().add(new PVector(0, -worldHeight-spawnerOffset)));
        }

        // collisions
        this.collisionHandler.process(delta);
        for(WaveSpawner spawner : this.spawners) spawner.process(delta);

        // powerUp
        this.powerUpTimer -= delta;
        this.gunPowerUpTimer -= delta;
        if (this.gunPowerUpTimer <= 0 && !(this.playerShip.getGun() instanceof BasicGun)) {
            BasicGun gun = new BasicGun();
            gun.addDamageMultiplier(this.playerShip.getGun().getDamageMultiplier()-1.0f); // dmg mult carries over
            this.playerShip.setGun(gun);
        }
    }


    public void draw(PApplet window) {
        if (this.powerUpTimer > 0.0f) {
            window.textAlign(PConstants.CENTER);
            window.fill(127);
            switch (this.powerUpType)  {
                case HEALTH -> window.text("HEALTH UP", this.worldWidth/2, this.worldHeight/2);
                case DAMAGE -> window.text("DAMAGE UP", this.worldWidth/2, this.worldHeight/2);
                case ARMOR -> window.text("ARMOR UP", this.worldWidth/2, this.worldHeight/2);
                case SHOTGUN -> window.text("SHOTGUN", this.worldWidth/2, this.worldHeight/2);
                case MACHINE -> window.text("MACHINEGUN", this.worldWidth/2, this.worldHeight/2);
            }
            window.textAlign(PConstants.LEFT);
        }
        ProjectileHandler.draw(window);
        ShipHandler.draw(window);
        PowerUpHandler.draw(window);
        for(int i = this.explosions.size()-1; i >= 0; i--) this.explosions.get(i).draw(window);
    }


    // overrides

     /**
     * @param event
     */
    @Override
    public void onDestroy(DestroyEvent event) {
        if (event.getDestroyed() instanceof Projectile destroyed) {
            PVector position = destroyed.getPosition().copy().add(destroyed.getBoundary().sub(new PVector(32, 32)).mult(0.5f));
            AnimatedSprite explosion = new AnimatedSprite("explosion", position, 3, true);
            this.explosions.add(explosion);
            explosion.addObserver(this);
        } else if (event.getDestroyed() instanceof Ship destroyed) {
            PVector position = destroyed.getPosition().copy().add(destroyed.getBoundary().sub(new PVector(32, 32)).mult(0.5f));
            AnimatedSprite explosion1 = new AnimatedSprite("explosion", position, 8, true);
            AnimatedSprite explosion2 = new AnimatedSprite("explosion", position.copy().add(PVector.random2D().mult(12.0f)), 5, true);
            this.explosions.add(explosion1);
            this.explosions.add(explosion2);
            explosion1.addObserver(this);
            explosion2.addObserver(this);
        } else if (event.getDestroyed() instanceof AnimatedSprite) {
            this.explosions.remove((AnimatedSprite)event.getDestroyed());
        } else if (event.getDestroyed() instanceof PowerUp) {
            this.powerUpTimer = powerUpShowTime;
            this.player.addScore(50);
            switch (event.getDestroyed().getClass().getName()) {
                case "galaga.gameObjects.powerups.ArmorPower" -> this.powerUpType = PowerUpType.ARMOR;
                case "galaga.gameObjects.powerups.DamagePower" -> this.powerUpType = PowerUpType.DAMAGE;
                case "galaga.gameObjects.powerups.HealthPower" -> this.powerUpType = PowerUpType.HEALTH;
                case "galaga.gameObjects.powerups.MachinePower" -> {this.powerUpType = PowerUpType.MACHINE; this.gunPowerUpTimer = powerUpActiveTime;}
                case "galaga.gameObjects.powerups.ShotgunPower" -> {this.powerUpType = PowerUpType.SHOTGUN; this.gunPowerUpTimer = powerUpActiveTime;}
            }
        }

        if (event.getDestroyed() instanceof Enemy) {
            switch (event.getDestroyed().getClass().getName()) {
                case "galaga.gameObjects.enemies.Shooter" -> this.player.addScore(30);
                case "galaga.gameObjects.enemies.ShooterBuffed" -> this.player.addScore(45);
                case "galaga.gameObjects.enemies.Small" -> this.player.addScore(5);
                case "galaga.gameObjects.enemies.SmallBuffed" -> this.player.addScore(10);
                case "galaga.gameObjects.enemies.Speedy" -> this.player.addScore(50);
                case "galaga.gameObjects.enemies.SpeedyBuffed" -> this.player.addScore(100);
                case "galaga.gameObjects.enemies.Tanky" -> this.player.addScore(20);
                case "galaga.gameObjects.enemies.TankyBuffed" -> this.player.addScore(40);
            }
        }
    }

    /**
     * @param event
     */
    @Override
    public void onHit(HitEvent event) {
        AnimatedSprite explosion = new AnimatedSprite("explosion", event.getCollisionPoint(), 3, true);
        this.explosions.add(explosion);
        explosion.addObserver(this);

        if (event.getHitted() instanceof PlayerShip) this.player.addScore(-100);
    }

    /**
     * @param event
     */
    @Override
    public void update(Event event) {
        if (event instanceof DestroyEvent)
            this.onDestroy((DestroyEvent)event);
        else if (event instanceof HitEvent)
            this.onHit((HitEvent)event);
    }

    // constructors

    public Engine(Player player) {
        this.player = player;
        this.collisionHandler = new CollisionHandler();
    }

    // getters/setters

    public Player getPlayer() {
        return this.player;
    }
}
