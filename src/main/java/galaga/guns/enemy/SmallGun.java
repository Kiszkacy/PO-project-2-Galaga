package galaga.guns.enemy;

import galaga.gameObjects.projectiles.enemy.SlowProjectile;
import galaga.guns.ProjectileGun;

import static galaga.constants.Global.enemyScalingStart;
import static galaga.constants.Global.enemyScalingStop;

public class SmallGun extends ProjectileGun {

    private static final float scalingSpeed = 90.0f; // per minute

    // constructors

    public SmallGun(float time) {
        super();
        this.damage = 1.0f;
        this.projectileSpread = 0.0f;
        this.shotSpread = 0.0f;
        this.evenSpreadDistribution = 0.0f;
        this.shotsPerSecond = 0.2f;
        this.barrelCount = 1;
        this.projectile = new SlowProjectile();
        float minutes = Math.min(Math.max(time-enemyScalingStart, 0.0f), (enemyScalingStop-enemyScalingStart)) / 60.0f;
        this.launchSpeed = 150.0f + minutes * scalingSpeed;
        this.shotCooldown = 2.0f;
    }
}
