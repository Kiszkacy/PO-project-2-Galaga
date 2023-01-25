package galaga.gameObjects.projectiles;

import galaga.gameObjects.AnimatedSprite;
import galaga.gameObjects.Entity;
import galaga.singletons.SpriteHandler;
import galaga.interfaces.Collidable;
import galaga.interfaces.Destroyable;
import galaga.interfaces.Launchable;
import galaga.interfaces.Spriteable;
import galaga.observer.DestroyEvent;
import galaga.util.Color;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;

import static galaga.util.EasyPrint.pcol;

abstract public class Projectile extends Entity implements Launchable, Destroyable {

    protected float damage;
    protected float lifetime;
    protected float aliveFor;
    protected int maxPenetration;
    protected int penetrationLeft;
    protected AnimatedSprite sprite;
    protected LinkedList<Collidable> collisionHistory = new LinkedList<>();


    abstract public Projectile copy();

    // overrides

    /**
     * @param delta
     */
    @Override
    public void process(float delta) {
        this.movementProcess(delta);
        this.aliveFor += delta;
        if (this.aliveFor >= this.lifetime) this.destroy();
    }

    /**
     * @param window
     */
    @Override
    public void draw(PApplet window) {
        this.sprite.draw(window);
    }

    /**
     * @param from
     * @param direction
     * @param speed
     */
    @Override
    public void launch(PVector from, PVector direction, float speed) {
        this.position = from;
        this.sprite.setPosition(this.position);
        this.velocity = direction.copy().normalize().mult(speed);
        this.direction = direction;
    }

    /**
     *
     */
    @Override
    public void destroy() {
        this.notify(new DestroyEvent(this));
    }

    /**
     * @param with
     * @return
     */
    @Override
    public boolean isColliding(Collidable with) {
        if (this.collisionHistory.contains(with)) return false;
        return super.isColliding(with);
    }

    // constructors

    public Projectile() {
        super();
        this.aliveFor = 0.0f;
    }


    public Projectile(PVector boundary, ArrayList<Integer> collideLayers, float damage, float lifetime,
                      int maxPenetration, String spriteName) {
        super();
        this.boundary = boundary;
        this.collideLayers = collideLayers;
        this.damage = damage;
        this.lifetime = lifetime;
        this.aliveFor = 0.0f;
        this.maxPenetration = maxPenetration;
        this.penetrationLeft = maxPenetration;
        this.sprite = new AnimatedSprite(spriteName, this.position, 20);
    }


    public Projectile(PVector boundary, ArrayList<Integer> collideLayers, float damage, float lifetime,
                      int maxPenetration, String spriteName, int spriteFrames) {
        super();
        this.boundary = boundary;
        this.collideLayers = collideLayers;
        this.damage = damage;
        this.lifetime = lifetime;
        this.aliveFor = 0.0f;
        this.maxPenetration = maxPenetration;
        this.penetrationLeft = maxPenetration;
        this.sprite = new AnimatedSprite(spriteName, this.position, spriteFrames);
    }


    public Projectile(ArrayList<Integer> collideLayers) {
        super();
        this.collideLayers = collideLayers;
    }


    // getters/setters

    public float getLifetime() {
        return this.lifetime;
    }


    public void setLifetime(float lifetime) {
        this.lifetime = lifetime;
    }


    public float getAliveFor() {
        return this.aliveFor;
    }


    public int getMaxPenetration() {
        return this.maxPenetration;
    }


    public void setMaxPenetration(int maxPenetration) {
        this.maxPenetration = maxPenetration;
    }


    public float getDamage() {
        return this.damage;
    }


    public void setDamage(float damage) {
        this.damage = damage;
    }


    public int getPenetrationLeft() {
        return this.penetrationLeft;
    }


    public void setPenetrationLeft(int penetrationLeft) {
        this.penetrationLeft = penetrationLeft;
        if (this.penetrationLeft <= 0) this.destroy();
    }


    public LinkedList<Collidable> getCollisionHistory() {
        return collisionHistory;
    }


    /**
     * @param position
     */
    @Override
    public void setPosition(PVector position) {
        super.setPosition(position);
        this.sprite.setPosition(position);
    }
}
