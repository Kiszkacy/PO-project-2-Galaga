package galaga.observer;

import galaga.gameObjects.projectiles.Projectile;
import galaga.interfaces.Collidable;
import galaga.interfaces.Damageable;
import galaga.interfaces.Destroyable;
import processing.core.PVector;

import java.util.Objects;

public class HitEvent implements Event {

    private final Damageable hitted;
    private final Collidable by;
    private final PVector collisionPoint;

    // overrides

    /**
     * @return
     */
    @Override
    public Object[] getAllData() {
        return new Object[]{this.hitted, this.by, this.collisionPoint};
    }

    // constructors

    public HitEvent(Damageable hitted, Collidable by, PVector collisionPoint) {
        this.hitted = hitted;
        this.by = by;
        this.collisionPoint = collisionPoint;
    }

    // getters/setters

    public Damageable getHitted() {
        return this.hitted;
    }


    public Collidable getBy() {
        return this.by;
    }


    public PVector getCollisionPoint() {
        return this.collisionPoint;
    }

    // hash & equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HitEvent hitEvent = (HitEvent) o;
        return Objects.equals(this.hitted, hitEvent.hitted) && Objects.equals(this.by, hitEvent.by) && Objects.equals(this.collisionPoint, hitEvent.collisionPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hitted, this.by, this.collisionPoint);
    }
}
