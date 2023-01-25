package galaga.interfaces;

public interface Damageable extends Destroyable {
    float getHealth();
    void setHealth(float health);
    void damage(float amount);
    void heal(float amount);
}
