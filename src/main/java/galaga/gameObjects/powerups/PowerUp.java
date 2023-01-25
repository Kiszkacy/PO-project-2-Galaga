package galaga.gameObjects.powerups;

import galaga.constants.Layers;
import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.Entity;
import galaga.gameObjects.Ship;
import galaga.interfaces.Destroyable;
import galaga.interfaces.Launchable;
import galaga.observer.DestroyEvent;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

abstract public class PowerUp extends Entity implements Launchable, Destroyable {

    protected AnimatedSprite sprite;


    abstract public void activateOn(Ship ship);

    // overrides

    /**
     *
     */
    @Override
    public void destroy() {
        this.notify(new DestroyEvent(this));
    }

    /**
     * @param from
     * @param direction
     * @param speed
     */
    @Override
    public void launch(PVector from, PVector direction, float speed) {
        this.position = from;
        this.sprite.setPosition(this.position);
        this.velocity = direction.copy().normalize().mult(speed);
        this.direction = direction;
    }

    /**
     * @param window
     */
    @Override
    public void draw(PApplet window) {
        this.sprite.draw(window);
    }

    // constructors

    public PowerUp() {
        super();
        this.collideLayers.add(Layers.POWERUP.id());
        this.direction = new PVector(0,1);
        this.boundary = new PVector(32,32);
    }

    public PowerUp(ArrayList<Integer> collideLayers) {
        super();
        this.collideLayers = collideLayers;
    }
}
