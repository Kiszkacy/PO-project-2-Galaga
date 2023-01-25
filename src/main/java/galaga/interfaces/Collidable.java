package galaga.interfaces;

import processing.core.PVector;

import java.util.ArrayList;

public interface Collidable extends Placeable {
    ArrayList<Integer> getCollideLayers();
    void setCollideLayers(ArrayList<Integer> layers);
    boolean addCollideLayers(ArrayList<Integer> layers);
    boolean removeCollideLayers(ArrayList<Integer> layers);
    PVector getBoundary();
    boolean setBoundary(PVector boundary);
    boolean isColliding(Collidable with);
}
