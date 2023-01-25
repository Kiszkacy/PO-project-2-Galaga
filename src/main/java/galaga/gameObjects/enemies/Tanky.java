package galaga.gameObjects.enemies;

import galaga.gameObjects.AnimatedSprite;
import galaga.guns.NullGun;
import galaga.movementTypes.MovementType;
import galaga.movementTypes.SinusoidalMovement;
import processing.core.PVector;

import static galaga.constants.Global.enemyScalingStart;
import static galaga.constants.Global.enemyScalingStop;

public class Tanky extends Enemy {

    private static final String spriteIdentifier = "enemy-tanky-normal";
    private static final float HP = 3.0f;
    private static final float scalingHP = 2.5f; // per minute
    private static final float speed = 90.0f;
    private static final float scalingSpeed = 45.0f; // per minute

    // constructors

    public Tanky(float time, boolean sinusoidal) {
        this();
        float minutes = Math.min(Math.max(time-enemyScalingStart, 0.0f), (enemyScalingStop-enemyScalingStart)) / 60.0f;
        this.maxHealth = HP + Math.round(minutes * scalingHP);
        this.health = this.maxHealth;
        this.launchSpeed = speed + minutes * scalingSpeed;
        this.boundary = new PVector(52, 50);
        this.sprite = new AnimatedSprite(spriteIdentifier, this.position, 15);
        this.gun = new NullGun();
        if (sinusoidal) this.movementType = new SinusoidalMovement(2.0f, 64.0f, this.launchSpeed);
    }


    public Tanky() {
        super();
    }
}


