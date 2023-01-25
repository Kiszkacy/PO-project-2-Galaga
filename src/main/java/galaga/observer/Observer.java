package galaga.observer;

/**
 * Defines object that can handle events created by other objects.
 */
public interface Observer {

    void update(Event event);
}
