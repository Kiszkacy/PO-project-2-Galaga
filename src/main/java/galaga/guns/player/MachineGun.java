package galaga.guns.player;

import galaga.gameObjects.projectiles.player.BasicProjectile;
import galaga.gameObjects.projectiles.player.MachineProjectile;
import galaga.guns.ProjectileGun;

public class MachineGun extends ProjectileGun {

    // constructors

    public MachineGun() {
        super();
        this.damage = 1.0f;
        this.projectileSpread = 0.0f;
        this.shotSpread = 30.0f;
        this.evenSpreadDistribution = 0.0f;
        this.shotsPerSecond = 10.0f;
        this.barrelCount = 1;
        this.projectile = new MachineProjectile();
        this.launchSpeed = 650.0f;
    }
}
