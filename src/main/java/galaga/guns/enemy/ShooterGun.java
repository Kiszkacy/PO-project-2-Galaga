package galaga.guns.enemy;

import galaga.gameObjects.projectiles.enemy.FastProjectile;
import galaga.guns.ProjectileGun;

import static galaga.constants.Global.enemyScalingStart;
import static galaga.constants.Global.enemyScalingStop;

public class ShooterGun extends ProjectileGun {

    private static final float scalingSpeed = 150.0f; // per minute

    // constructors

    public ShooterGun(float time) {
        super();
        this.damage = 1.0f;
        this.projectileSpread = 0.0f;
        this.shotSpread = 0.0f;
        this.evenSpreadDistribution = 0.0f;
        this.shotsPerSecond = 0.5f;
        this.barrelCount = 1;
        this.projectile = new FastProjectile();
        float minutes = Math.min(Math.max(time-enemyScalingStart, 0.0f), (enemyScalingStop-enemyScalingStart)) / 60.0f;
        this.launchSpeed = 450.0f + minutes * scalingSpeed;
        this.shotCooldown = 0.5f;
    }
}
