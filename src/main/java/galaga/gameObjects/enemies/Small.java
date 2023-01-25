package galaga.gameObjects.enemies;

import galaga.gameObjects.AnimatedSprite;
import galaga.guns.enemy.SmallGun;
import galaga.movementTypes.MovementType;
import galaga.movementTypes.SinusoidalMovement;
import processing.core.PVector;

import static galaga.constants.Global.enemyScalingStart;
import static galaga.constants.Global.enemyScalingStop;

public class Small extends Enemy {

    private static final String spriteIdentifier = "enemy-small-normal";
    private static final float HP = 1.0f;
    private static final float scalingHP = 1.0f; // per minute
    private static final float speed = 90.0f;
    private static final float scalingSpeed = 45.0f; // per minute

    // constructors

    public Small(float time, boolean sinusoidal) {
        this();
        float minutes = Math.min(Math.max(time-enemyScalingStart, 0.0f), (enemyScalingStop-enemyScalingStart)) / 60.0f;
        this.maxHealth = HP + Math.round(minutes * scalingHP);
        this.health = this.maxHealth;
        this.launchSpeed = speed + minutes * scalingSpeed;
        this.boundary = new PVector(32, 32);
        this.sprite = new AnimatedSprite(spriteIdentifier, this.position, 15);
        this.gun = new SmallGun(time);
        if (sinusoidal) this.movementType = new SinusoidalMovement(2.0f, 64.0f, this.launchSpeed);
    }


    public Small() {
        super();
    }
}
