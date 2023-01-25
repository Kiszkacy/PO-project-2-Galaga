package galaga.guns;

import galaga.gameObjects.projectiles.player.BasicProjectile;

public class NullGun extends ProjectileGun {

    // constructors

    public NullGun() {
        super();
        this.damage = 0.0f;
        this.projectileSpread = 0.0f;
        this.shotSpread = 0.0f;
        this.evenSpreadDistribution = 0.0f;
        this.shotsPerSecond = 1.0f;
        this.barrelCount = 0;
        this.projectile = new BasicProjectile();
        this.launchSpeed = 0.0f;
    }
}
