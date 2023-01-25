package galaga.gameObjects.projectiles.enemy;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.projectiles.Projectile;
import processing.core.PVector;

import java.util.ArrayList;

public class FastProjectile extends Projectile {

    // overrides

    /**
     * @return
     */
    @Override
    public Projectile copy() {
        return new FastProjectile();
    }

    // constructors

    public FastProjectile() {
        super();
        this.boundary = new PVector(20, 48);
        this.damage = 1.0f;
        this.lifetime = 2.5f;
        this.maxPenetration = 1;
        this.penetrationLeft = 1;
        this.sprite = new AnimatedSprite("enemy", this.position, 20);
    }


    public FastProjectile(ArrayList<Integer> collideLayers) {
        this();
        this.collideLayers = collideLayers;
    }
}
