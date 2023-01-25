package galaga.guns.player;

import galaga.gameObjects.projectiles.player.BasicProjectile;
import galaga.guns.ProjectileGun;

public class BasicGun extends ProjectileGun {

    // constructors

    public BasicGun() {
        super();
        this.damage = 1.0f;
        this.projectileSpread = 0.0f;
        this.shotSpread = 6.0f;
        this.evenSpreadDistribution = 0.0f;
        this.shotsPerSecond = 5.2f;
        this.barrelCount = 1;
        this.projectile = new BasicProjectile();
        this.launchSpeed = 560.0f;
    }
}
