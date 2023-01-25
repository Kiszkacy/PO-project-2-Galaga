package galaga.interfaces;

import processing.core.PVector;

public interface Movable extends Placeable {
    PVector getVelocity(); void setVelocity(PVector velocity);
    PVector getDirection(); void setDirection(PVector direction);
    void movementProcess(float delta);
}
