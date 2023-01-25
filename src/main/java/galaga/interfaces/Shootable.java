package galaga.interfaces;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public interface Shootable {

    float getDamage(); void setDamage(float damage);
    float getDamageMultiplier(); void multiplyDamageMultiplier(float amount); void addDamageMultiplier(float amount);
    void process(float delta);
    boolean shoot(PVector from, PVector direction, ArrayList<Integer> layers);
    boolean canShoot();
}
