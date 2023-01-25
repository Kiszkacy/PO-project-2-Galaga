package galaga.gameObjects.powerups;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.Ship;
import galaga.guns.player.BasicGun;
import galaga.guns.player.ShotGun;
import galaga.interfaces.Shootable;

public class ShotgunPower extends PowerUp {

    /**
     * @param ship
     */
    @Override
    public void activateOn(Ship ship) {
        ShotGun gun = new ShotGun();
        gun.addDamageMultiplier(ship.getGun().getDamageMultiplier()-1.0f); // dmg mult carries over
        ship.setGun(gun);
    }

    // constructors

    public ShotgunPower() {
        super();
        this.sprite = new AnimatedSprite("shotgun", this.position, 30);
    }
}
