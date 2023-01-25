package galaga.guns;

import galaga.gameObjects.projectiles.Projectile;
import galaga.interfaces.Shootable;
import galaga.singletons.ProjectileHandler;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static processing.core.PApplet.radians;

public class ProjectileGun implements Shootable {
    // STATS
    protected float damage;
    protected float damageMultiplier;
    protected float projectileSpread;
    protected float shotSpread;
    protected float evenSpreadDistribution; // TODO not implemented yet
    protected float shotsPerSecond;
    protected float barrelCount;
    protected Projectile projectile;
    protected float launchSpeed;
    // LOGIC
    protected float shotCooldown = 0.0f;

    // overrides


    /**
     * @param delta
     */
    @Override
    public void process(float delta) {
        this.shotCooldown -= delta;
    }

    /**
     * @return
     */
    @Override
    public boolean shoot(PVector from, PVector direction, ArrayList<Integer> layers) {
        Random r = new Random();
        float shotSpread = radians(this.shotSpread * r.nextFloat() - this.shotSpread/2.0f);
        // calc evenDistribution step
        float step = (this.barrelCount != 1) ? this.evenSpreadDistribution / (this.barrelCount-1) : 0;
        for (int i = 0; i < this.barrelCount; i++) {
            float projectileSpread = radians(this.projectileSpread * r.nextFloat() - this.projectileSpread/2.0f + step*i - this.evenSpreadDistribution/2.0f);
            PVector launchDirection = direction.copy().rotate(shotSpread+projectileSpread);
            Projectile projectile = this.projectile.copy();
            projectile.setCollideLayers(layers);
            projectile.setDamage(this.damage*this.damageMultiplier);
            projectile.launch(from.copy().sub(this.projectile.getBoundary().x/2, 0), launchDirection.copy(), this.launchSpeed);
            projectile.setDirection(launchDirection.copy().normalize());
            ProjectileHandler.registerProjectile(projectile);
        }
        this.shotCooldown = 1.0f / this.shotsPerSecond;
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean canShoot() {
        return (this.shotCooldown <= 0.0f);
    }

    /**
     * @return
     */
    @Override
    public float getDamage() {
        return this.damage;
    }

    /**
     *
     */
    @Override
    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     * @return
     */
    @Override
    public float getDamageMultiplier() {
        return this.damageMultiplier;
    }

    /**
     *
     */
    @Override
    public void multiplyDamageMultiplier(float amount) {
        this.damageMultiplier *= amount;
    }

    /**
     *
     */
    @Override
    public void addDamageMultiplier(float amount) {
        this.damageMultiplier += amount;
    }

    // constructors

    public ProjectileGun() {
        this.damageMultiplier = 1.0f;
    }

    public ProjectileGun(float damage, float projectileSpread, float shotSpread, float evenSpreadDistribution, int shotsPerSecond, int barrelCount,
                        Projectile projectile, float launchSpeed) {
        this.damage = damage;
        this.damageMultiplier = 1.0f;
        this.projectileSpread = projectileSpread;
        this.shotSpread = shotSpread;
        this.evenSpreadDistribution = evenSpreadDistribution;
        this.shotsPerSecond = shotsPerSecond;
        this.barrelCount = barrelCount;
        this.projectile = projectile;
        this.launchSpeed = launchSpeed;
    }

    // hash & equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectileGun that = (ProjectileGun) o;
        return Float.compare(that.damage, this.damage) == 0 && Float.compare(that.damageMultiplier, this.damageMultiplier) == 0 &&
                Float.compare(that.projectileSpread, this.projectileSpread) == 0 && Float.compare(that.shotSpread, this.shotSpread) == 0 &&
                Float.compare(that.evenSpreadDistribution, this.evenSpreadDistribution) == 0 && Float.compare(that.shotsPerSecond, this.shotsPerSecond) == 0 &&
                Float.compare(that.barrelCount, this.barrelCount) == 0 && Float.compare(that.launchSpeed, this.launchSpeed) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.damage, this.damageMultiplier, this.projectileSpread, this.shotSpread,
                this.evenSpreadDistribution, this.shotsPerSecond, this.barrelCount, this.launchSpeed);
    }
}
