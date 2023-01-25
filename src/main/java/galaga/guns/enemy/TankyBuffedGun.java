package galaga.guns.enemy;

import galaga.gameObjects.projectiles.enemy.BuffedTankyProjectile;
import galaga.guns.ProjectileGun;


public class TankyBuffedGun extends ProjectileGun {

    // constructors

    public TankyBuffedGun() {
        super();
        this.damage = 1.0f;
        this.projectileSpread = 4.0f;
        this.shotSpread = 4.0f;
        this.evenSpreadDistribution = 32.0f;
        this.shotsPerSecond = 0.3f;
        this.barrelCount = 4;
        this.projectile = new BuffedTankyProjectile();
        this.launchSpeed = 250.0f;
    }
}
