package galaga.gameObjects.projectiles.enemy;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.projectiles.Projectile;
import processing.core.PVector;

import java.util.ArrayList;

public class BuffedTankyProjectile extends Projectile {

    // overrides

    /**
     * @return
     */
    @Override
    public Projectile copy() {
        return new BuffedTankyProjectile();
    }

    // constructors

    public BuffedTankyProjectile() {
        super();
        this.boundary = new PVector(24, 24);
        this.damage = 1.0f;
        this.lifetime = 5.5f;
        this.maxPenetration = 1;
        this.penetrationLeft = 1;
        this.acceleration = -35.0f;
        this.sprite = new AnimatedSprite("tanky-buffed", this.position, 20);
    }


    public BuffedTankyProjectile(ArrayList<Integer> collideLayers) {
        this();
        this.collideLayers = collideLayers;
    }
}
