package galaga.guns.player;

import galaga.gameObjects.projectiles.player.ShotgunProjectile;
import galaga.guns.ProjectileGun;

public class ShotGun extends ProjectileGun {

    // constructors

    public ShotGun() {
        super();
        this.damage = 1.0f;
        this.projectileSpread = 4.0f;
        this.shotSpread = 4.0f;
        this.evenSpreadDistribution = 25.0f;
        this.shotsPerSecond = 2.5f;
        this.barrelCount = 3;
        this.projectile = new ShotgunProjectile();
        this.launchSpeed = 550.0f;
    }
}
