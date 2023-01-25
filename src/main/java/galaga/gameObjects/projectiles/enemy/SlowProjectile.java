package galaga.gameObjects.projectiles.enemy;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.projectiles.Projectile;
import galaga.movementTypes.SinusoidalMovement;
import processing.core.PVector;

import java.util.ArrayList;

public class SlowProjectile extends Projectile {

    // overrides

    /**
     * @return
     */
    @Override
    public Projectile copy() {
        return new SlowProjectile();
    }

    /**
     * @param from
     * @param direction
     * @param speed
     */
    @Override
    public void launch(PVector from, PVector direction, float speed) {
        super.launch(from, direction, speed);
        this.movementType = new SinusoidalMovement(2.0f, 64.0f, speed); // TODO ugly trick | linear only ?
    }

    // constructors

    public SlowProjectile() {
        super();
        this.boundary = new PVector(20, 48);
        this.damage = 1.0f;
        this.lifetime = 5.2f;
        this.maxPenetration = 1;
        this.penetrationLeft = 1;
        this.sprite = new AnimatedSprite("enemy", this.position, 20);
    }
    
    
    public SlowProjectile(ArrayList<Integer> collideLayers) {
        this();
        this.collideLayers = collideLayers;
    }
}
