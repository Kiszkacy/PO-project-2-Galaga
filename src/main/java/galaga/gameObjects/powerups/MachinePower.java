package galaga.gameObjects.powerups;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.Ship;
import galaga.guns.ProjectileGun;
import galaga.guns.player.MachineGun;
import galaga.guns.player.ShotGun;
import galaga.interfaces.Shootable;

public class MachinePower extends PowerUp {

    /**
     * @param ship
     */
    @Override
    public void activateOn(Ship ship) {
        MachineGun gun = new MachineGun();
        gun.addDamageMultiplier(ship.getGun().getDamageMultiplier()-1.0f); // dmg mult carries over
        ship.setGun(gun);
    }

    // constructors

    public MachinePower() {
        super();
        this.sprite = new AnimatedSprite("machine", this.position, 30);
    }
}
