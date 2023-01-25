package galaga.gameObjects.projectiles.player;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.projectiles.Projectile;
import galaga.movementTypes.SinusoidalMovement;
import processing.core.PVector;

import java.util.ArrayList;


public class MachineProjectile extends Projectile {

    // overrides

    /**
     * @return
     */
    @Override
    public Projectile copy() {
        return new MachineProjectile();
    }

    /**
     * @param from
     * @param direction
     * @param speed
     */
    @Override
    public void launch(PVector from, PVector direction, float speed) {
        super.launch(from, direction, speed);
        this.movementType = new SinusoidalMovement(32.0f, 384.0f, speed); // TODO ugly trick
    }

    // constructors

    public MachineProjectile() {
        super();
        this.boundary = new PVector(24, 24);
        this.damage = 1.0f;
        this.lifetime = 1.1f;
        this.maxPenetration = 1;
        this.penetrationLeft = 1;
        this.acceleration = -400.0f;
        this.sprite = new AnimatedSprite("player-machine", this.position, 20);
    }


    public MachineProjectile(ArrayList<Integer> collideLayers) {
        this();
        this.collideLayers = collideLayers;
    }
}
