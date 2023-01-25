package galaga.gameObjects.projectiles.player;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.projectiles.Projectile;
import processing.core.PVector;

import java.util.ArrayList;


public class ShotgunProjectile extends Projectile {

    // overrides

    /**
     * @return
     */
    @Override
    public Projectile copy() {
        return new ShotgunProjectile();
    }

    // constructors

    public ShotgunProjectile() {
        super();
        this.boundary = new PVector(32, 32);
        this.damage = 1.0f;
        this.lifetime = 1.2f;
        this.maxPenetration = 2;
        this.penetrationLeft = 2;
        this.acceleration = -320.0f;
        this.sprite = new AnimatedSprite("player-shotgun", this.position, 20);
    }


    public ShotgunProjectile(ArrayList<Integer> collideLayers) {
        this();
        this.collideLayers = collideLayers;
    }
}
