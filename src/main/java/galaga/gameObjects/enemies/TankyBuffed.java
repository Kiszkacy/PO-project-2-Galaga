package galaga.gameObjects.enemies;

import galaga.constants.PowerUpType;
import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.powerups.*;
import galaga.guns.enemy.TankyBuffedGun;
import galaga.movementTypes.MovementType;
import galaga.movementTypes.SinusoidalMovement;
import galaga.singletons.PowerUpHandler;
import processing.core.PVector;

import java.util.Random;

import static galaga.constants.Global.*;
import static galaga.constants.Global.powerUpSpeed;

public class TankyBuffed extends Enemy {

    private static final String spriteIdentifier = "enemy-tanky-buffed";
    private static final float HP = 5.0f;
    private static final float scalingHP = 5.0f; // per minute
    private static final float speed = 90.0f;
    private static final float scalingSpeed = 45.0f; // per minute

    // overrides

    /**
     *
     */
    @Override
    public void destroy() {
        super.destroy();

        boolean powerUpDrop = new Random().nextFloat() <= powerUpDropChance;
        if (powerUpDrop) {
            PowerUpType powerUpType = PowerUpType.values()[new Random().nextInt(PowerUpType.values().length)];
            PowerUp powerUp = null;
            switch (powerUpType) {
                case HEALTH -> PowerUpHandler.registerPowerUp(powerUp = new HealthPower());
                case ARMOR -> PowerUpHandler.registerPowerUp(powerUp = new ArmorPower());
                case DAMAGE -> PowerUpHandler.registerPowerUp(powerUp = new DamagePower());
                case MACHINE -> PowerUpHandler.registerPowerUp(powerUp = new MachinePower());
                case SHOTGUN -> PowerUpHandler.registerPowerUp(powerUp = new ShotgunPower());
            }
            powerUp.launch(this.position, this.direction, powerUpSpeed);
        }
    }

    // constructors

    public TankyBuffed(float time, boolean sinusoidal) {
        this();
        float minutes = Math.min(Math.max(time-enemyScalingStart, 0.0f), (enemyScalingStop-enemyScalingStart)) / 60.0f;
        this.maxHealth = HP + Math.round(minutes * scalingHP);
        this.health = this.maxHealth;
        this.launchSpeed = speed + minutes * scalingSpeed;
        this.boundary = new PVector(52, 50);
        this.sprite = new AnimatedSprite(spriteIdentifier, this.position, 15);
        this.gun = new TankyBuffedGun();
        if (sinusoidal) this.movementType = new SinusoidalMovement(2.0f, 64.0f, this.launchSpeed);
    }


    public TankyBuffed() {
        super();
    }
}
