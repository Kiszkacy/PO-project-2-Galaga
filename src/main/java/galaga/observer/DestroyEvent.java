package galaga.observer;

import galaga.interfaces.Destroyable;

import java.util.Objects;

public class DestroyEvent implements Event {

    private final Destroyable destroyed;

    // overrides

    /**
     * @return
     */
    @Override
    public Object[] getAllData() {
        return new Object[]{this.destroyed};
    }

    // constructors

    public DestroyEvent(Destroyable destroyed) {
        this.destroyed = destroyed;
    }

    // getters/setters

    public Destroyable getDestroyed() {
        return this.destroyed;
    }

    // hash & equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DestroyEvent that = (DestroyEvent) o;
        return Objects.equals(this.destroyed, that.destroyed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.destroyed);
    }
}
