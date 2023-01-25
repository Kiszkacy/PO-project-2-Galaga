package galaga.gameObjects.enemies;

import galaga.gameObjects.AnimatedSprite;
import galaga.guns.NullGun;
import galaga.guns.enemy.ShooterGun;
import processing.core.PVector;

import static galaga.constants.Global.enemyScalingStart;
import static galaga.constants.Global.enemyScalingStop;

public class Speedy extends Enemy {

    private static final String spriteIdentifier = "enemy-shooter-normal"; // TODO NEW TEXTURES?
    private static final float HP = 1.0f;
    private static final float scalingHP = 0.5f; // per minute
    private static final float speed = 350.0f;
    private static final float scalingSpeed = 100.0f; // per minute

    // constructors

    public Speedy(float time) {
        this();
        float minutes = Math.min(Math.max(time-enemyScalingStart, 0.0f), (enemyScalingStop-enemyScalingStart)) / 60.0f;
        this.maxHealth = HP + Math.round(minutes * scalingHP);
        this.health = this.maxHealth;
        this.launchSpeed = speed + minutes * scalingSpeed;
        this.boundary = new PVector(64, 32); // TODO NEW TEXTURES?
        this.sprite = new AnimatedSprite(spriteIdentifier, this.position, 15);
        this.gun = new NullGun();
    }


    public Speedy() {
        super();
    }
}
