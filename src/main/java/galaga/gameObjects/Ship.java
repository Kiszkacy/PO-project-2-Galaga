package galaga.gameObjects;

import galaga.singletons.SpriteHandler;
import galaga.interfaces.Damageable;
import galaga.interfaces.Shootable;
import galaga.interfaces.Spriteable;
import galaga.observer.DestroyEvent;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import java.util.ArrayList;

abstract public class Ship extends Entity implements Damageable {

    protected float health;
    protected float maxHealth;
    protected float maxSpeed;
    protected float deceleration;
    protected Shootable gun;
    protected PVector shotDirection;
    protected AnimatedSprite sprite;
    // LOGIC
    protected boolean isFlying = false;


    public void shoot() {
        if (this.gun.canShoot()) {
            this.gun.shoot(this.position.copy().add(this.boundary.x/2, 0), this.shotDirection, this.collideLayers);
        }
    }

    // overrides

    /**
     * @param delta
     */
    @Override
    public void process(float delta) {
        this.movementProcess(delta);
        this.gun.process(delta);
    }

    /**
     * @param window
     */
    @Override
    public void draw(PApplet window) {
//        window.fill(255, 255, 255, 64);
//        window.rect(this.position.x, this.position.y, this.boundary.x, this.boundary.y);
        this.sprite.draw(window);
    }

    /**
     * @param delta
     */
    @Override
    public void movementProcess(float delta) {
        this.movementType.calculate(delta, this);
        this.velocity.add(this.direction.copy().mult(this.acceleration*delta)).limit(this.maxSpeed);
        this.position.add(this.velocity.copy().mult(delta));
        if (!this.isFlying) this.velocity.lerp(new PVector(0,0), Math.min(1.0f, this.deceleration * delta)); // TODO ugly min
    }

    /**
     * @param amount
     */
    @Override
    public void damage(float amount) {
        this.setHealth(this.health-amount);
    }

    /**
     * @param amount
     */
    @Override
    public void heal(float amount) {
        this.setHealth(Math.min(this.health+amount, this.maxHealth));
    }

    /**
     *
     */
    @Override
    public void destroy() { // TODO observer
        this.notify(new DestroyEvent(this));
    }

    // constructors

    public Ship() {
        super();
    }


    public Ship(float maxHealth, float maxSpeed, float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName) {
        super();
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.gun = gun;
        this.shotDirection = shotDirection;
        this.sprite = new AnimatedSprite(spriteName, this.position, 20);
    }


    public Ship(float maxHealth, float maxSpeed, float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName, int spriteFrames) {
        super();
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.gun = gun;
        this.shotDirection = shotDirection;
        this.sprite = new AnimatedSprite(spriteName, this.position, spriteFrames);
    }


    public Ship(PVector position, PVector boundary, ArrayList<Integer> collideLayers, float maxHealth, float maxSpeed,
                float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName) {
        super(position, boundary, collideLayers);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.gun = gun;
        this.shotDirection = shotDirection;
        this.sprite = new AnimatedSprite(spriteName, this.position, 20);
    }


    public Ship(PVector position, PVector boundary, ArrayList<Integer> collideLayers, float maxHealth, float maxSpeed,
                float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName, int spriteFrames) {
        super(position, boundary, collideLayers);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.gun = gun;
        this.shotDirection = shotDirection;
        this.sprite = new AnimatedSprite(spriteName, this.position, spriteFrames);
    }


    public Ship(PVector position, PVector direction, PVector boundary, ArrayList<Integer> collideLayers, float maxHealth, float maxSpeed,
                float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName) {
        super(position, direction, boundary, collideLayers);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.gun = gun;
        this.shotDirection = shotDirection;
        this.sprite = new AnimatedSprite(spriteName, this.position, 20);
    }


    public Ship(PVector position, PVector direction, PVector boundary, ArrayList<Integer> collideLayers, float maxHealth, float maxSpeed,
                float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName, int spriteFrames) {
        super(position, direction, boundary, collideLayers);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.gun = gun;
        this.shotDirection = shotDirection;
        this.sprite = new AnimatedSprite(spriteName, this.position, spriteFrames);
    }

    // getters/setters

    /**
     * @return
     */
    @Override
    public float getHealth() {
        return this.health;
    }

    /**
     *
     */
    @Override
    public void setHealth(float health) {
        this.health = health;
        if (health <= 0) this.destroy();
    }


    public float getMaxHealth() {
        return this.maxHealth;
    }


    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }


    public float getMaxSpeed() {
        return this.maxSpeed;
    }


    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }


    public float getDeceleration() {
        return this.deceleration;
    }


    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }


    public Shootable getGun() {
        return this.gun;
    }


    public void setGun(Shootable gun) {
        this.gun = gun;
    }


    public boolean isFlying() {
        return this.isFlying;
    }


    public void setFlying(boolean flying) {
        isFlying = flying;
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
