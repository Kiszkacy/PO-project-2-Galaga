package galaga.gameObjects.enemies;

import galaga.interfaces.Launchable;
import galaga.constants.Layers;
import galaga.gameObjects.Ship;
import galaga.interfaces.Shootable;
import processing.core.PVector;

import java.util.ArrayList;

abstract public class Enemy extends Ship implements Launchable {

    protected float launchSpeed;


    // overrides

    /**
     * @param delta
     */
    @Override
    public void process(float delta) {
        super.process(delta);
        this.shoot(); // shoot constantly if can
    }

    /**
     *
     */
    @Override
    public void shoot() {
        if (this.gun.canShoot()) {
            this.gun.shoot(this.position.copy().add(this.boundary.x/2, 8), new PVector(0,1), this.collideLayers);
        }
    }

    /**
     * @param from
     * @param direction
     * @param speed
     */
    @Override
    public void launch(PVector from, PVector direction, float speed) {
        this.isFlying = true;
        this.position = from;
        this.sprite.setPosition(this.position);
        this.velocity = direction.copy().normalize().mult(speed);
        this.direction = direction.copy().normalize();
    }

    // constructors

    public Enemy() {
        super();
        this.collideLayers.add(Layers.ENEMY.id());
        this.maxSpeed = Float.POSITIVE_INFINITY;
        this.direction = new PVector(0,1);
    }

    public Enemy(ArrayList<Integer> collideLayers) {
        super();
        this.collideLayers = collideLayers;
    }

    // getters/setters


    public float getLaunchSpeed() {
        return this.launchSpeed;
    }


    public void setLaunchSpeed(float launchSpeed) {
        this.launchSpeed = launchSpeed;
    }
}