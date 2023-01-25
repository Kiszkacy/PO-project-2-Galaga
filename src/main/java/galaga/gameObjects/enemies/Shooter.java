package galaga.gameObjects.enemies;

import galaga.gameObjects.AnimatedSprite;
import galaga.guns.enemy.ShooterGun;
import galaga.movementTypes.MovementType;
import processing.core.PVector;

import static galaga.constants.Global.enemyScalingStart;
import static galaga.constants.Global.enemyScalingStop;

public class Shooter extends Enemy {

    private static final String spriteIdentifier = "enemy-shooter-normal";
    private static final float HP = 1.0f;
    private static final float scalingHP = 1.0f; // per minute
    private static final float speed = 140.0f;
    private static final float scalingSpeed = 50.0f; // per minute
    private boolean evenShot = false;

    /**
     *
     */
    @Override
    public void shoot() {
        if (this.gun.canShoot()) {
            PVector shotPos = this.position.copy().add(this.boundary.x/2, 0);
            if (evenShot) shotPos.add(12, 8); else shotPos.add(-12, 8);
            this.gun.shoot(shotPos, new PVector(0,1), this.collideLayers);
            this.evenShot = !this.evenShot;
        }
    }

    // constructors

    public Shooter(float time) {
        this();
//        float scaleRatio = Math.min(Math.max((time - enemyScalingStart) / (enemyScalingStop - enemyScalingStart), 0.0f), 1.0f);
        float minutes = Math.min(Math.max(time-enemyScalingStart, 0.0f), (enemyScalingStop-enemyScalingStart)) / 60.0f;
        this.maxHealth = HP + Math.round(minutes * scalingHP);
        this.health = this.maxHealth;
        this.launchSpeed = speed + minutes * scalingSpeed;
        this.boundary = new PVector(64, 32);
        this.sprite = new AnimatedSprite(spriteIdentifier, this.position, 15);
        this.gun = new ShooterGun(time);
    }


    public Shooter() {
        super();
    }
}
