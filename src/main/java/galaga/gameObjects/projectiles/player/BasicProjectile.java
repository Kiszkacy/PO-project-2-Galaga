package galaga.gameObjects.projectiles.player;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.projectiles.Projectile;
import processing.core.PVector;

import java.util.ArrayList;

import static galaga.util.EasyPrint.p;


public class BasicProjectile extends Projectile {

    // overrides

    /**
     * @return
     */
    @Override
    public Projectile copy() {
        return new BasicProjectile();
    }

    // constructors

    public BasicProjectile() {
        super();
        this.boundary = new PVector(24, 24);
        this.damage = 1.0f;
        this.lifetime = 1.1f;
        this.maxPenetration = 1;
        this.penetrationLeft = 1;
        this.sprite = new AnimatedSprite("player-basic", this.position, 20);
    }


    public BasicProjectile(ArrayList<Integer> collideLayers) {
        this();
        this.collideLayers = collideLayers;
    }
}
