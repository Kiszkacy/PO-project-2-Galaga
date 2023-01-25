package galaga.interfaces;

import processing.core.PVector;

public interface Launchable extends Movable {
    void launch(PVector from, PVector direction, float speed);
}
