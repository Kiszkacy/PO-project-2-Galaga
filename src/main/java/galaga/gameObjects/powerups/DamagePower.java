package galaga.gameObjects.powerups;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.Ship;

public class DamagePower extends PowerUp {

    /**
     * @param ship
     */
    @Override
    public void activateOn(Ship ship) {
        ship.getGun().addDamageMultiplier(0.5f);
    }

    // constructors

    public DamagePower() {
        super();
        this.sprite = new AnimatedSprite("dmg", this.position, 30);
    }
}
