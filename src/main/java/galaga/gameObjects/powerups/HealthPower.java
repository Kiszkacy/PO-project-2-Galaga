package galaga.gameObjects.powerups;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.Ship;
import galaga.guns.player.MachineGun;

public class HealthPower extends PowerUp {

    /**
     * @param ship
     */
    @Override
    public void activateOn(Ship ship) {
        if (ship.getHealth() < ship.getMaxHealth())
            ship.heal(1);
    }

    // constructors

    public HealthPower() {
        super();
        this.sprite = new AnimatedSprite("hp", this.position, 30);
    }
}
