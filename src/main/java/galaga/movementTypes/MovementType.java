package galaga.movementTypes;

import galaga.interfaces.Movable;

public interface MovementType {

    void calculate(float delta, Movable object);
}
