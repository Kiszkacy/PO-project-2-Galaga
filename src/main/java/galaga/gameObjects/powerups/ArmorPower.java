package galaga.gameObjects.powerups;

import galaga.constants.Layers;
import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.Ship;
import galaga.singletons.SpriteHandler;
import processing.core.PVector;

public class ArmorPower extends PowerUp {

    /**
     * @param ship
     */
    @Override
    public void activateOn(Ship ship) {
        ship.heal(1);
    }

    // constructors

    public ArmorPower() {
        super();
        this.sprite = new AnimatedSprite("arm", this.position, 30);
    }
}
